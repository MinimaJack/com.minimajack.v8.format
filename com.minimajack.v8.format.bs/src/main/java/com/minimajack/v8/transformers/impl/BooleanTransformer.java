package com.minimajack.v8.transformers.impl;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;

import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.utility.V8Reader;

public class BooleanTransformer
    extends AbstractTransformer<Boolean>
{

    @Override
    public Boolean read( ParameterizedType type, ByteBuffer buffer )
    {
        return V8Reader.read( Integer.class, buffer ) == 1 ?  Boolean.TRUE : Boolean.FALSE;
    }

}
