package com.minimajack.v8.transformers;

import java.nio.ByteBuffer;

public abstract class AbstractArraysTransformer<T>
{
    public abstract T read( Class<?> type, ByteBuffer buffer );

    public T read( ByteBuffer buffer )
    {
        return this.read( null, buffer );
    }
}
