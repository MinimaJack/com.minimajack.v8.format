package com.minimajack.v8.transformers.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minimajack.v8.annotation.Required;
import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.annotation.V8Since;
import com.minimajack.v8.annotation.V8Transient;
import com.minimajack.v8.annotation.V8Version;
import com.minimajack.v8.transformers.AbstractClassTransformer;
import com.minimajack.v8.transformers.impl.field.FieldInfo;
import com.minimajack.v8.utility.V8Reader;

public class ClassTransformer
    extends AbstractClassTransformer<Object>
{
    final Logger logger = LoggerFactory.getLogger( ClassTransformer.class );

    private static final Map<Class<?>, List<FieldInfo>> cacheFields = new HashMap<Class<?>, List<FieldInfo>>();

    private static final Map<Class<?>, Method> afterUnmarshalMethods = new HashMap<Class<?>, Method>();

    private static final List<Class<?>> versionalClasses = new ArrayList<Class<?>>();

    @Override
    public Object read( Class<?> clazz, ByteBuffer buffer )
    {
        boolean isV8Class = clazz.getAnnotation( V8Class.class ) != null;
        boolean isHasVersion = clazz.getAnnotation( V8Version.class ) != null;
        Object object;
        if ( isV8Class )
        {
            return readV8Class( clazz, buffer, isHasVersion );
        }
        else
        {
            object = readClass( clazz, buffer, isHasVersion );
        }

        return object;
    }

    public Object readV8Class( Class<?> clazz, ByteBuffer buffer )
    {
        return readV8Class( clazz, buffer, false );
    }

    public Object readV8Class( Class<?> clazz, ByteBuffer buffer, boolean versional )
    {
        readBracket( buffer );

        Object object = readClass( clazz, buffer );

        readCloseBracket( buffer );

        runAfterUnmarshal( clazz, object );

        return object;
    }

    public Object readClass( Class<?> clazz, ByteBuffer buffer )
    {
        return readClass( clazz, buffer, false );
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
        List<FieldInfo> lfi = new ArrayList<FieldInfo>();
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

    public Object readClass( Class<?> clazz, ByteBuffer buffer, boolean versional )
    {
        Object object;
        try
        {
            object = clazz.newInstance();
        }
        catch ( InstantiationException | IllegalAccessException e )
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
                if ( field.required || buffer.get( buffer.position() ) != 0x7D
                    && buffer.get( buffer.position() ) != 0x0D )
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
                logger.debug( "Read field {} ", field.name );
                Class<?> fieldType = field.fieldType;
                Type paramType = field.paramType;
                Object fieldValue = V8Reader.read( fieldType, paramType, buffer );
                if ( field.isVersion )
                {
                    hasVersion = true;
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
