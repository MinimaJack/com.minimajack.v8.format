package com.minimajack.v8.transformers;

import com.minimajack.v8.utility.SerializedOutputStream;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;

public interface AbstractParametrizedTransformer<T> {
    T read(ParameterizedType type, ByteBuffer buffer);

    default T read(ByteBuffer buffer) {
        return this.read(null, buffer);
    }

    void write(Object object, SerializedOutputStream buffer);
}
