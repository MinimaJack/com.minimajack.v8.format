package com.minimajack.v8.transformers;

import java.nio.ByteBuffer;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.minimajack.v8.transformers.impl.MapTransformer;
import com.minimajack.v8.utility.V8Reader;

public class TestMapTransformer
{
    private static MapTransformer transformer;

    @BeforeClass
    public static void init()
    {
        V8Reader.init();
        transformer = new MapTransformer();
    }

    @Test
    public void simpleIntegerMap()
        throws NoSuchFieldException, SecurityException
    {

        Map<?, ?> data = transformer.read( Integer.class, Integer.class, ByteBuffer.wrap( "2,1,1,2,2".getBytes() ) );
        assertEquals( 2, data.size() );

    }

}
