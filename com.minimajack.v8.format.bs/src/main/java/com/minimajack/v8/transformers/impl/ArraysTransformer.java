package com.minimajack.v8.transformers.impl;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import com.minimajack.v8.transformers.AbstractArraysTransformer;
import com.minimajack.v8.utility.V8Reader;

public class ArraysTransformer
    extends AbstractArraysTransformer<Object[]>
{

    @Override
    public Object[] read( Class<?> clazz, ByteBuffer buffer )
    {
        Class<?> componentType = clazz.getComponentType();
        List<Object> list = new LinkedList<Object>();
        boolean readNext = true;
        while ( readNext )
        {
            list.add( V8Reader.read( componentType, buffer ) );
            readNext = buffer.hasRemaining() && buffer.get( buffer.position() ) == 0x2C;
            if ( readNext )
            {
                V8Reader.readChar( buffer, ',' );
            }
        }
        Object[] arr = (Object[]) Array.newInstance( componentType, list.size() );
        System.arraycopy( list.toArray(), 0, arr, 0, list.size() );
        return arr;
    }

}
