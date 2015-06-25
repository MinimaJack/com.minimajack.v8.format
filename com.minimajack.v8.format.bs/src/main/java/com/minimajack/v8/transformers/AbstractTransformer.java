package com.minimajack.v8.transformers;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;

public abstract class AbstractTransformer<T>
{
    public abstract T read( ParameterizedType type, ByteBuffer buffer );

    public T read( ByteBuffer buffer )
    {
        return this.read( null, buffer );
    }
}
