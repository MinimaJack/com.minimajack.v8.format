package com.minimajack.v8.transformers.impl;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import com.minimajack.v8.transformers.AbstractTransformer;

public class IntegerTransformer
    extends AbstractTransformer<Integer>
{

    @Override
    public Integer read( ParameterizedType type, ByteBuffer buffer )
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

}
