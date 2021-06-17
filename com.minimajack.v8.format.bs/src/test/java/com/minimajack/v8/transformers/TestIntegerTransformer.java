package com.minimajack.v8.transformers;

import com.minimajack.v8.transformers.impl.IntegerTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;


public class TestIntegerTransformer
{
    private static IntegerTransformer transformer;

    @BeforeClass
    public static void init()
    {
        transformer = new IntegerTransformer();
        V8Reader.init();
    }

    @Test
    public void simpleNumber()
    {
        Integer number = transformer.read( ByteBuffer.wrap( "123456".getBytes() ) );
        assertEquals(Integer.valueOf(123456), number );
    }

    @Test
    public void simpleNumberWrite()
    {
        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write( 123456,  baos);
        assertEquals("123456", baos.toString());

        baos = new SerializedOutputStream();
        V8Reader.write(Integer.valueOf(123456), baos);
        assertEquals("123456", baos.toString());
    }

    @Test
    public void simpleNumberWithWithTrailingChars()
    {
        Integer number = transformer.read( ByteBuffer.wrap( "123456  ".getBytes() ) );
        assertEquals(Integer.valueOf(123456), number );
        number = transformer.read( ByteBuffer.wrap( "123456,  ".getBytes() ) );
        assertEquals(Integer.valueOf(123456), number );
    }

    @Test
    public void negativeNumber()
    {
        Integer number = transformer.read( ByteBuffer.wrap( "-123456".getBytes() ) );
        assertEquals(Integer.valueOf(-123456), number );
    }

    @Test
    public void negativeNumberWrite()
    {
        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write( -123456,  baos);
        assertEquals("-123456", baos.toString());
        baos = new SerializedOutputStream();
        V8Reader.write(Integer.valueOf(-123456), baos);
        assertEquals("-123456", baos.toString());
    }

    @Test
    public void negativeNumberWithTrailingChars()
    {
        Integer number = transformer.read( ByteBuffer.wrap( "-123   ".getBytes() ) );
        assertEquals(Integer.valueOf(-123), number );
    }

    @Test
    public void uIntNumberTest()
    {
        Integer number = transformer.read( ByteBuffer.wrap( "4294967295".getBytes() ) );
        assertEquals(Integer.valueOf(-1), number );
    }

    @Test(expected = NumberFormatException.class)
    public void badNegativeNumberWithTrailingChars()
    {
        transformer.read( ByteBuffer.wrap( "--".getBytes() ) );
    }

}
