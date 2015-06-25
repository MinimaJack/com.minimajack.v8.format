package com.minimajack.v8.transformers.impl;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.minimajack.v8.exeptions.NotValidUUID;
import com.minimajack.v8.transformers.AbstractTransformer;

/**
 * Read UUID
 * @author e.vanzhula
 *
 */
public class UUIDTransformer
    extends AbstractTransformer<UUID>
{

    /**
     * Represent default UUID size
     */
    private static final int UUID_SIZE_IN_STRING = 36;

    @Override
    public UUID read( ParameterizedType type, ByteBuffer buffer )
    {
        byte[] guid = new byte[UUID_SIZE_IN_STRING];
        buffer.get( guid );
        String guidString = new String( guid, StandardCharsets.UTF_8 );
        try
        {
            return UUID.fromString( guidString );
        }
        catch ( Exception e )
        {
            throw new NotValidUUID( e );
        }
    }

}
