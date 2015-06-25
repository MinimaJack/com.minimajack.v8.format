package com.minimajack.v8.transformers.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.utility.V8Reader;

public class MapTransformer
    extends AbstractTransformer<Map<?, ?>>
{

    @Override
    public Map<?, ?> read( ParameterizedType type, ByteBuffer buffer )
    {
        Type[] types = type.getActualTypeArguments();
        Class<?> firstClass = (Class<?>) types[0];
        Class<?> secondClass = (Class<?>) types[1];
        return read( firstClass, secondClass, buffer );
    }

    public Map<?, ?> read( Class<?> keyClass, Class<?> valueClass, ByteBuffer buffer )
    {
        HashMap<Object, Object> map = new LinkedHashMap<Object, Object>();

        Integer count = V8Reader.read( Integer.class, buffer );
        for ( int i = 0; i < count; i++ )
        {
            V8Reader.readChar( buffer, ',' );
            Object key = V8Reader.read( keyClass, buffer );
            V8Reader.readChar( buffer, ',' );
            Object value = V8Reader.read( valueClass, buffer );
            map.put( key, value );
        }
        return map;
    }
}
