package com.minimajack.v8.transformers;

import java.nio.ByteBuffer;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.minimajack.v8.transformers.impl.ArraysTransformer;
import com.minimajack.v8.utility.V8Reader;

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

}
