package com.minimajack.v8.transformers.impl;

import com.minimajack.v8.transformers.AbstractClassTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ArraysTransformer
    extends AbstractClassTransformer<Object[]>
{

    @Override
    public Object[] read( Class<?> clazz, ByteBuffer buffer )
    {
        Class<?> componentType = clazz.getComponentType();
        List<Object> list = new ArrayList<>();
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
        Object[] arr = (Object[]) Array.newInstance( componentType, 0 );
        return list.toArray(arr);
    }

    @Override
    public void write(Object object, SerializedOutputStream buffer) {
        this.write((Object[])object, buffer);
    }

    public void write(Object[] object, SerializedOutputStream buffer ){

        for (int i = 0; i <object.length; i++) {
            V8Reader.write(object[i], null, buffer);
            if(i != object.length-1)
                buffer.putComa();
        }
    }

}
