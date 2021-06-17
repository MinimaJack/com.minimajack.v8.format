package com.minimajack.v8.transformers.impl;

import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;

import java.nio.ByteBuffer;

public class BooleanTransformer
        implements AbstractTransformer<Boolean> {

    @Override
    public Boolean read(ByteBuffer buffer) {
        return V8Reader.read(Integer.class, buffer) == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public void write(Object object, SerializedOutputStream buffer) {
        buffer.write((Boolean)object? 0x31 : 0x30);
    }

}
