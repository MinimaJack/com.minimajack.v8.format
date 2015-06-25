package com.minimajack.v8.transformers.impl;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;

import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.utility.AnyData;

/**
 * Read all except comma and brackets
 * @author e.vanzhula
 *
 */
public class AnyDataTransformer
    extends AbstractTransformer<AnyData>
{
    /**
     * <b>,</b> ascii - code
     */
    private static final byte COMMA = 0x2C;

    /**
     * <b>{</b> - ascii code 
     */
    private static final byte START_BKT = 0x7B;

    /**
     * <b>}</b> - ascii code 
     */
    private static final byte END_BKT = 0x7D;

    @Override
    public AnyData read( ParameterizedType type, ByteBuffer buffer )
    {
        StringBuilder sb = new StringBuilder();

        while ( buffer.hasRemaining() )
        {
            int value = buffer.get();
            if ( value != COMMA && value != START_BKT && value != END_BKT )
            {
                sb.append( (char) value );
            }
            else
            {
                buffer.position( buffer.position() - 1 );
                break;
            }
        }
        return new AnyData( sb.toString() );
    }

}
