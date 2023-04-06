package com.minimajack.v8.transformers.impl;

import com.minimajack.v8.exeptions.NotValidUUID;
import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

/**
 * Read UUID
 * @author e.vanzhula
 *
 */
public class UUIDTransformer
    implements AbstractTransformer<UUID>
{

    /**
     * Represent default UUID size
     */
    private static final int UUID_SIZE_IN_STRING = 36;
    private static final byte[] ZERO_STRING = "00000000-0000-0000-0000-000000000000".getBytes(StandardCharsets.UTF_8);
    public static final UUID ZERO_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    @Override
    public UUID read( ByteBuffer buffer )
    {
        byte[] guid = new byte[UUID_SIZE_IN_STRING];
        buffer.get( guid );

        if(Arrays.equals(guid, ZERO_STRING)){
            return ZERO_UUID;
        }
        try
        {
            String guidString = new String( guid, StandardCharsets.UTF_8 );
            return UUID.fromString( guidString );
        }
        catch ( Exception e )
        {
            throw new NotValidUUID( e );
        }
    }

    @Override
    public void write(Object object, SerializedOutputStream buffer) {
        buffer.writeBytes(object.toString().getBytes(StandardCharsets.UTF_8));
    }

}
