package com.minimajack.v8.transformers;

import com.minimajack.v8.utility.SerializedOutputStream;

import java.nio.ByteBuffer;

public interface AbstractTransformer<T>
{
    T read( ByteBuffer buffer );

    void write(Object object, SerializedOutputStream buffer );
}
