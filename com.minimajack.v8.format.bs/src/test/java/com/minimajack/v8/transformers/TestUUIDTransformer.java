package com.minimajack.v8.transformers;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.minimajack.v8.exeptions.NotValidUUID;
import com.minimajack.v8.transformers.impl.UUIDTransformer;

public class TestUUIDTransformer
{
    private static UUIDTransformer transformer;

    @BeforeClass
    public static void init()
    {
        transformer = new UUIDTransformer();
    }

    @Test
    public void simpleData()
    {
        UUID data = transformer.read( ByteBuffer.wrap( "2bcef0d1-0981-11d6-b9b8-0050bae0a95d".getBytes() ) );
        assertEquals( UUID.fromString( "2bcef0d1-0981-11d6-b9b8-0050bae0a95d" ), data );
    }

    @Test(expected = NotValidUUID.class)
    public void badData()
    {
        transformer.read( ByteBuffer.wrap( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes() ) );
    }
}
