package com.minimajack.v8.transformers;

import com.minimajack.v8.utility.SerializedOutputStream;

import java.nio.ByteBuffer;

public abstract class AbstractClassTransformer<T>
{
    public abstract T read( Class<?> type, ByteBuffer buffer );

    public abstract void write(Object object, SerializedOutputStream buffer);
}
