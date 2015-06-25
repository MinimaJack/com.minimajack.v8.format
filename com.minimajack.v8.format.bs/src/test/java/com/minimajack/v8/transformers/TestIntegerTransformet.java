package com.minimajack.v8.transformers;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.minimajack.v8.transformers.impl.IntegerTransformer;

public class TestIntegerTransformet
{
    private static IntegerTransformer transformer;

    @BeforeClass
    public static void init()
    {
        transformer = new IntegerTransformer();
    }

    @Test
    public void simpleNumber()
    {
        Integer number = transformer.read( ByteBuffer.wrap( "123456".getBytes() ) );
        assertEquals( new Integer( 123456 ), number );
    }

    @Test
    public void simpleNumberWithWithTrailingChars()
    {
        Integer number = transformer.read( ByteBuffer.wrap( "123456  ".getBytes() ) );
        assertEquals( new Integer( 123456 ), number );
        number = transformer.read( ByteBuffer.wrap( "123456,  ".getBytes() ) );
        assertEquals( new Integer( 123456 ), number );
    }

    @Test
    public void negaiveNumber()
    {
        Integer number = transformer.read( ByteBuffer.wrap( "-123456".getBytes() ) );
        assertEquals( new Integer( -123456 ), number );
    }

    @Test
    public void negaiveNumberWithTrailingChars()
    {
        Integer number = transformer.read( ByteBuffer.wrap( "-123   ".getBytes() ) );
        assertEquals( new Integer( -123 ), number );
    }

    @Test(expected = NumberFormatException.class)
    public void badNegaiveNumberWithTrailingChars()
    {
        transformer.read( null, ByteBuffer.wrap( "--".getBytes() ) );
    }
}
