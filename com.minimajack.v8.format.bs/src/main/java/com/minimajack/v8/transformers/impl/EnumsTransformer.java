package com.minimajack.v8.transformers.impl;

import com.minimajack.v8.transformers.AbstractClassTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class EnumsTransformer
    extends AbstractClassTransformer<Enum<?>>
{

    @Override
    public Enum<?> read( Class<?> clazz, ByteBuffer buffer )
    {
        return (Enum<?>) readEnumOrdinal( clazz, buffer );
    }

    @Override
    public void write(Object object, SerializedOutputStream buffer) {
        this.write((Enum<?>) object, buffer);
    }

    public static <T> T readEnumOrdinal( Class<T> clazz, ByteBuffer buffer )
    {
        Integer value = V8Reader.read( Integer.class, buffer );
        return clazz.getEnumConstants()[value];
    }

    public void write(Enum<?> object, SerializedOutputStream buffer ){

        buffer.writeBytes(String.valueOf(object.ordinal()).getBytes(StandardCharsets.UTF_8));

    }

}
