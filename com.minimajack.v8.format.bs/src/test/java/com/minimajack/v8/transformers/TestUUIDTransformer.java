package com.minimajack.v8.transformers;

import com.minimajack.v8.exeptions.NotValidUUID;
import com.minimajack.v8.transformers.impl.UUIDTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TestUUIDTransformer
{
    private static UUIDTransformer transformer;

    @BeforeClass
    public static void init()
    {
        transformer = new UUIDTransformer();
        V8Reader.init();

    }

    @Test
    public void simpleData()
    {
        UUID data = transformer.read( ByteBuffer.wrap( "2bcef0d1-0981-11d6-b9b8-0050bae0a95d".getBytes() ) );
        assertEquals( UUID.fromString( "2bcef0d1-0981-11d6-b9b8-0050bae0a95d" ), data );
    }

    @Test
    public void sameZeroData()
    {
        UUID data = transformer.read( ByteBuffer.wrap( "00000000-0000-0000-0000-000000000000".getBytes() ) );
        UUID data2 = transformer.read( ByteBuffer.wrap( "00000000-0000-0000-0000-000000000000".getBytes() ) );
        assertEquals(data,data2);
        assertEquals(UUIDTransformer.ZERO_UUID, data);
        assertEquals(UUIDTransformer.ZERO_UUID, data2);
    }


    @Test(expected = NotValidUUID.class)
    public void badData()
    {
        transformer.read( ByteBuffer.wrap( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes() ) );
    }

    @Test
    public void simpleDataWrite()
    {
        UUID data = UUID.fromString( "2bcef0d1-0981-11d6-b9b8-0050bae0a95d" );
        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write( data,  baos);
        assertEquals("2bcef0d1-0981-11d6-b9b8-0050bae0a95d", baos.toString());

        baos = new SerializedOutputStream();
        V8Reader.write(data, baos);
        assertEquals("2bcef0d1-0981-11d6-b9b8-0050bae0a95d", baos.toString());

    }
}
