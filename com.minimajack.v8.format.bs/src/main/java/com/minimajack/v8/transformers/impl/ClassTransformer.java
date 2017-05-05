package com.minimajack.v8.transformers.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import com.minimajack.v8.annotation.Required;
import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.annotation.V8Transient;
import com.minimajack.v8.transformers.AbstractClassTransformer;
import com.minimajack.v8.utility.V8Reader;

public class ClassTransformer
    extends AbstractClassTransformer<Object>
{

    @Override
    public Object read( Class<?> clazz, ByteBuffer buffer )
    {
        boolean isV8Class = clazz.getAnnotation( V8Class.class ) != null;

        if ( isV8Class )
        {
            readBracket( buffer );
        }

        Object object;
        try
        {
            object = clazz.newInstance();
        }
        catch ( InstantiationException | IllegalAccessException e )
        {
            throw new RuntimeException( "Cant instantinate class" + clazz );
        }

        Field[] fields = clazz.getDeclaredFields();
        boolean first = true;
        try
        {
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

                Required req = field.getAnnotation( Required.class );
                if ( req == null || req.required() || buffer.get( buffer.position() ) != 0x7D && buffer.get( buffer.position() ) !=  0x0D)
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

                Object fieldValue = null;
                Class<?> fieldType = field.getType();
                Type paramType = field.getGenericType();
                fieldValue = V8Reader.read( fieldType, paramType, buffer );

                field.set( object, fieldValue );
                first = false;

            }
        }
        catch ( IllegalArgumentException | IllegalAccessException e )
        {
            throw new RuntimeException( e );
        }
        if ( isV8Class )
        {
            readCloseBracket( buffer );
        }

        runAfterUnmarshal( object );

        return object;
    }

    private void runAfterUnmarshal( Object object )
    {
        try
        {
            for ( Method method : object.getClass().getMethods() )
            {
                if ( method.getName().equalsIgnoreCase( "afterUnmarshal" ) )
                {
                    method.invoke( object );
                }
            }
        }
        catch ( SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1 )
        {
            throw new RuntimeException( "Can't run adterUnmarshal method" );
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
