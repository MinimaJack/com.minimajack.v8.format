package com.minimajack.v8.transformers.impl;

import com.minimajack.v8.annotation.*;
import com.minimajack.v8.transformers.AbstractClassTransformer;
import com.minimajack.v8.transformers.impl.field.FieldInfo;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectTransformer
    extends AbstractClassTransformer<Object>
{
    final Logger logger = LoggerFactory.getLogger( ObjectTransformer.class );

    private static final Map<Class<?>, List<FieldInfo>> cacheFields = new HashMap<>();

    private static final Map<Class<?>, Method> afterUnmarshalMethods = new HashMap<>();

    private static final List<Class<?>> versionalClasses = new ArrayList<>();

    @Override
    public Object read( Class<?> clazz, ByteBuffer buffer )
    {
        boolean isV8Class = clazz.getAnnotation( V8Class.class ) != null;
        Object object;
        if ( isV8Class )
        {
            return readV8Class( clazz, buffer);
        }
        else
        {
            object = readClass( clazz, buffer);
        }

        return object;
    }

    @Override
    public void write(Object object, SerializedOutputStream buffer) {
        Class clazz = object.getClass();
        boolean isV8Class = clazz.getAnnotation( V8Class.class ) != null;
        if ( isV8Class )
        {
            writeV8Class( object, clazz, buffer );
        }
        else
        {
            writeClass( object, clazz, buffer);
        }
    }

    private void writeClass(Object object, Class clazz, SerializedOutputStream buffer) {
        {
            List<FieldInfo> fields = cacheFields.get( clazz );
            if ( fields == null )
            {
                fields = processClass( clazz );
            }
            boolean first = true;
            boolean hasVersion = versionalClasses.contains( clazz );
            Integer version = 0;
            try
            {
                for ( FieldInfo field : fields )
                {
                    if ( hasVersion )
                    {
                        V8Since since = field.since;
                        if ( since != null )
                        {
                            if ( version < since.version() || version >= since.removed() )
                            {
                                continue;
                            }
                        }
                    }
                    if ( !field.required  )
                    {
                        if(field.field.get(object) == null)
                        break;
                    }
                    if ( !first )
                    {
                        buffer.putComa();
                    }
                    Class<?> fieldType = field.fieldType;
                    logger.debug( "Write field {} {}", field.name, fieldType );
                    Type paramType = field.paramType;
                    V8Reader.write( field.field.get(object), paramType, buffer );
                    if ( field.isVersion )
                    {
                        hasVersion = true;
                        version = (Integer) field.field.get(object);
                    }

                    first = false;

                }
            }
            catch ( IllegalArgumentException | IllegalAccessException e )
            {
                throw new RuntimeException( e );
            }

        }
    }

    public void writeV8Class( Object object, Class<?> clazz, SerializedOutputStream buffer)
    {
        buffer.putOpenBracket();

        writeClass( object, clazz, buffer );

        buffer.putCloseBracket();

    }

    public Object readV8Class( Class<?> clazz, ByteBuffer buffer)
    {
        readBracket( buffer );

        Object object = readClass( clazz, buffer );

        readCloseBracket( buffer );

        runAfterUnmarshal( clazz, object );

        return object;
    }

    public List<FieldInfo> processClass( Class<?> clazz )
    {
        Field[] fields = clazz.getDeclaredFields();

        V8Version v8clver = clazz.getAnnotation( V8Version.class );
        if ( v8clver != null )
        {
            if ( !fields[0].getName().equals( "version" ) )
            {
                throw new RuntimeException( "bad version: " + clazz.getName() );
            }
        }
        List<FieldInfo> lfi = new ArrayList<>();
        for ( Field field : fields )
        {
            if ( field.getAnnotation( V8Transient.class ) != null )
            {
                continue;
            }
            if ( ( field.getModifiers() & java.lang.reflect.Modifier.FINAL ) == java.lang.reflect.Modifier.FINAL )
            {
                continue;
            }
            if ( ( field.getModifiers() & Modifier.PRIVATE ) == java.lang.reflect.Modifier.PRIVATE )
            {
                field.setAccessible(true);
            }

            FieldInfo fi = new FieldInfo();
            fi.field = field;
            fi.fieldType = field.getType();
            fi.paramType = field.getGenericType();
            fi.since = field.getAnnotation( V8Since.class );
            fi.name = field.getName();
            Required req = field.getAnnotation( Required.class );
            fi.required = req == null || req.required();
            if ( field.getAnnotation( V8Version.class ) != null )
            {
                fi.isVersion = true;
                versionalClasses.add( clazz );
            }
            lfi.add( fi );
        }
        cacheFields.put( clazz, lfi );

        for ( Method method : clazz.getMethods() )
        {
            if ( method.getName().equalsIgnoreCase( "afterUnmarshal" ) )
            {
                afterUnmarshalMethods.put( clazz, method );
                break;
            }
        }
        return lfi;
    }

    public Object readClass( Class<?> clazz, ByteBuffer buffer )
    {
        Object object;
        try
        {
            object = clazz.getConstructor().newInstance();
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e )
        {
            throw new RuntimeException( "Cant instantinate class" + clazz );
        }

        List<FieldInfo> fields = cacheFields.get( clazz );
        if ( fields == null )
        {
            fields = processClass( clazz );
        }
        boolean first = true;
        boolean hasVersion = versionalClasses.contains( clazz );
        Integer version = 0;
        try
        {
            for ( FieldInfo field : fields )
            {
                if ( hasVersion )
                {
                    V8Since since = field.since;
                    if ( since != null )
                    {
                        if ( version < since.version() || version >= since.removed() )
                        {
                            continue;
                        }
                    }
                }
                char nextSymbol = V8Reader.nextChar( buffer );
                if ( field.required || nextSymbol != (char)0x7D
                    && nextSymbol != (char)0x0D )
                {
                    if ( !first )
                    {
                        V8Reader.readChar( buffer, ',' );
                    }
                }
                else
                {
                    break;
                }
                Class<?> fieldType = field.fieldType;
                logger.debug( "Read field {} {}", field.name, fieldType );
                Type paramType = field.paramType;
                Object fieldValue = V8Reader.read( fieldType, paramType, buffer );
                if ( hasVersion && field.isVersion )
                {
                    version = (Integer) fieldValue;
                }
                logger.debug( "VALUE: {} ", fieldValue );
                logger.debug( "EndRead: {} ", field.name );

                field.field.set( object, fieldValue );
                first = false;

            }
        }
        catch ( IllegalArgumentException | IllegalAccessException e )
        {
            throw new RuntimeException( e );
        }

        return object;
    }

    private void runAfterUnmarshal( Class<?> clazz, Object object )
    {
        try
        {
            Method method = afterUnmarshalMethods.get( clazz );
            if ( method != null )
            {
                method.invoke( object );
            }
        }
        catch ( SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1 )
        {
            throw new RuntimeException( "Can't run afterUnmarshal method" );
        }
    }

    public static void readBracket( ByteBuffer buffer )
    {
        V8Reader.readChar( buffer, '{' );
    }

    public static void readCloseBracket( ByteBuffer buffer )
    {
        V8Reader.readChar( buffer, '}' );
    }
}
