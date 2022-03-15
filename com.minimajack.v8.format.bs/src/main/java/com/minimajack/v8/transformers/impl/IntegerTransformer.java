package com.minimajack.v8.transformers.impl;

import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class IntegerTransformer
    implements AbstractTransformer<Integer>
{

    @Override
    public Integer read( ByteBuffer buffer )
    {

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        while ( buffer.hasRemaining() )
        {
            int value = buffer.get();
            if ( value >= 0x30 && value <= 0x3A )
            {
                sb.append( (char) value );
            }
            else if ( first && value >= 0x2D )
            {
                sb.append( (char) value );
            }
            else
            {
                buffer.position( buffer.position() - 1 );
                break;
            }
            first = false;
        }

        return Long.valueOf( sb.toString() ).intValue();
    }

    @Override
    public void write(Object object, SerializedOutputStream buffer) {

        buffer.writeBytes(String.valueOf(object).getBytes(StandardCharsets.UTF_8));
    }

}
