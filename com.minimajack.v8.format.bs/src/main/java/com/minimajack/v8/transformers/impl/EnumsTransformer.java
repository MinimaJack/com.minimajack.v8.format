package com.minimajack.v8.transformers.impl;

import java.nio.ByteBuffer;
import com.minimajack.v8.annotation.Enumerated;
import com.minimajack.v8.transformers.AbstractEnumTransformer;
import com.minimajack.v8.utility.V8Reader;

public class EnumsTransformer
    extends AbstractEnumTransformer<Enum<?>>
{

    @Override
    public Enum<?> read( Class<?> clazz, ByteBuffer buffer )
    {
        Enumerated enums = clazz.getAnnotation( Enumerated.class );
        if ( enums != null && !enums.ordinal() )
        {
            return readEnumByName( clazz, buffer );
        }
        else
        {
            return (Enum<?>) readEnumOrdinal( clazz, buffer );
        }
    }

    public static <T> T readEnumOrdinal( Class<T> clazz, ByteBuffer buffer )
    {
        Integer value = V8Reader.read( Integer.class, buffer );
        return clazz.getEnumConstants()[value];
    }

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public static <T> T readEnumByName( Class clazz, ByteBuffer buffer )
    {
        String value = V8Reader.read( String.class, buffer );
        if ( value.equals( "#" ) )
        {
            value = "L";
        }
        return (T) Enum.valueOf( clazz, value.trim().toUpperCase() );
    }
}
