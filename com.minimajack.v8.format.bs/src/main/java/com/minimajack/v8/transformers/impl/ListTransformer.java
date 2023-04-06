package com.minimajack.v8.transformers.impl;

import com.minimajack.v8.transformers.AbstractParametrizedTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ListTransformer
    implements AbstractParametrizedTransformer<List<?>>
{

    @Override
    public List<?> read( ParameterizedType type, ByteBuffer buffer )
    {
        Type[] types = type.getActualTypeArguments();
        Class<?> firstClass = (Class<?>) types[0];
        return this.read( firstClass, buffer );
    }

    @Override
    public void write(Object object, SerializedOutputStream buffer) {
        List<Object> list = (List<Object>)object;
        Integer count = list.size();
        V8Reader.write(count, buffer);
        for( Object element : list ){
            buffer.putComa( );
            V8Reader.write( element, buffer );
        }

    }

    public List<?> read( Class<?> clazz, ByteBuffer buffer )
    {
        Integer count = V8Reader.read( Integer.class, buffer );
        List<Object> list = new ArrayList<>(count);

        for ( int i = 0; i < count; i++ )
        {
            V8Reader.readChar( buffer, ',' );
            list.add( V8Reader.read( clazz, buffer ) );

        }
        return list;
    }
}
