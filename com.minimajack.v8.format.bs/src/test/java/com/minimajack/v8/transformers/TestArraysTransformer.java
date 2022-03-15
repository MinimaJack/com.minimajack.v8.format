package com.minimajack.v8.transformers;

import com.minimajack.v8.transformers.impl.ArraysTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestArraysTransformer
{
    private static ArraysTransformer transformer;

    @BeforeClass
    public static void init()
    {
        V8Reader.init();
        transformer = new ArraysTransformer();
    }

    @Test
    public void simpleIntegerArray()
    {
        Integer[] valid = { 1, 2, 3, 4, 5, 6 };
        Integer[] data = (Integer[]) transformer.read( Integer[].class, ByteBuffer.wrap( "1,2,3,4,5,6".getBytes() ) );

        assertArrayEquals( valid, data );
    }

    @Test
    public void simpleStringArray()
    {
        String[] valid = { "zero", "one", "two" };
        String[] data = (String[]) transformer.read( String[].class,
                                                     ByteBuffer.wrap( "\"zero\", \"one\", \"two\"".getBytes() ) );

        assertArrayEquals( valid, data );
    }

    @Test
    public void simpleStringArrayWrite()
    {
        String[] valid = { "zero", "one", "two" };
        SerializedOutputStream baos = new SerializedOutputStream();

        transformer.write(valid, baos );

        assertEquals( "\"zero\",\"one\",\"two\"", baos.toString() );

    }
    @Test
    public void simpleStringArrayWriteReader()
    {
        String[] valid = { "zero", "one", "two" };
        SerializedOutputStream baos = new SerializedOutputStream();

        baos = new SerializedOutputStream();
        V8Reader.write(valid, baos);
        assertEquals( "\"zero\",\"one\",\"two\"", baos.toString() );
    }
}
