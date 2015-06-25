package com.minimajack.v8.transformers.impl;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.minimajack.v8.transformers.AbstractTransformer;

public class StringTransformer
    extends AbstractTransformer<String>
{

    @Override
    public String read( ParameterizedType type, ByteBuffer buffer )
    {
        int value;
        int state = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        while ( buffer.remaining() > 0 )
        {
            value = buffer.get();
            int prevState = state;
            if ( value == 0x22 )
            {
                if ( state == 0 )
                {
                    state = 1;
                }
                else if ( state == 1 )
                {
                    state = 2;
                }
                else if ( state == 2 )
                {
                    state = 1;
                }
            }
            else if ( state == 2 )
            {
                buffer.position( buffer.position() - 1 );
                break;
            }
            if ( prevState != 0 && state != 2 )
            {
                baos.write( value );
            }
        }

        return new String( baos.toByteArray(), StandardCharsets.UTF_8 );
    }

}
