package com.minimajack.v8.transformers.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.utility.V8Reader;

public class ListTransformer
    extends AbstractTransformer<List<?>>
{

    @Override
    public List<?> read( ParameterizedType type, ByteBuffer buffer )
    {
        Type[] types = type.getActualTypeArguments();
        Class<?> firstClass = (Class<?>) types[0];
        return this.read( firstClass, buffer );
    }

    public List<?> read( Class<?> clazz, ByteBuffer buffer )
    {
        List<Object> list = new ArrayList<>();
        Integer count = V8Reader.read( Integer.class, buffer );

        for ( int i = 0; i < count; i++ )
        {
            V8Reader.readChar( buffer, ',' );
            list.add( V8Reader.read( clazz, buffer ) );

        }
        return list;
    }
}
