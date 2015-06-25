package com.minimajack.v8.transformers;

import java.nio.ByteBuffer;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.minimajack.v8.transformers.impl.ListTransformer;
import com.minimajack.v8.utility.V8Reader;

public class TestListTransformer
{
    private static ListTransformer transformer;

    @BeforeClass
    public static void init()
    {
        V8Reader.init();
        transformer = new ListTransformer();
    }

    @Test
    public void simpleIntegerList()
        throws NoSuchFieldException, SecurityException
    {

        List<?> data = transformer.read( Integer.class, ByteBuffer.wrap( "3,1,2,3".getBytes() ) );
        assertEquals( 3, data.size() );

    }

}
