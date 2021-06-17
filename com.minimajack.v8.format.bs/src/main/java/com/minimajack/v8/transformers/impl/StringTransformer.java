package com.minimajack.v8.transformers.impl;

import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class StringTransformer
    implements AbstractTransformer<String>
{

    @Override
    public String read(ByteBuffer buffer )
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
                else
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

    @Override
    public void write(Object object, SerializedOutputStream buffer){
        buffer.write((byte) 0x22);
        buffer.writeBytes(((String)object).replace("\"", "\"\"").getBytes(StandardCharsets.UTF_8));
        buffer.write((byte) 0x22);
    }

}
