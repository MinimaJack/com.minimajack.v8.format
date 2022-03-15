package com.minimajack.v8.transformers;

import com.minimajack.v8.transformers.impl.AnyDataTransformer;
import com.minimajack.v8.utility.AnyData;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class TestAnyDataTransformer
{
    private static AnyDataTransformer transformer;

    @BeforeClass
    public static void init()
    {
        transformer = new AnyDataTransformer();
        V8Reader.init();
    }

    @Test
    public void simpleData()
    {
        AnyData data = transformer.read( ByteBuffer.wrap( "123456".getBytes() ) );
        assertEquals( new AnyData( "123456" ), data );
        data = transformer.read( ByteBuffer.wrap( "1234,56".getBytes() ) );
        assertEquals( new AnyData( "1234" ), data );
    }

    @Test
    public void simpleDataWrite()
    {
        AnyData data = new AnyData("123456");
        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write( data,  baos);
        assertEquals("123456", baos.toString());
    }


    @Test
    public void simpleDataMultiLine()
    {
        AnyData data = transformer.read( ByteBuffer.wrap( "123\n4\n456".getBytes() ) );
        assertEquals( new AnyData( "123\n4\n456" ), data );
        assertEquals( 3, data.getData().split( "\n" ).length );
    }

    @Test
    public void simpleDataBrackets()
    {
        AnyData data = transformer.read( ByteBuffer.wrap( "123{".getBytes() ) );
        assertEquals( new AnyData( "123" ), data );
    }

    @Test
    public void zeroData()
    {
        AnyData data = transformer.read( ByteBuffer.wrap( "{".getBytes() ) );
        assertEquals( new AnyData( "" ), data );
    }

    @Test
    public void zeroDataWrite()
    {
        AnyData data = new AnyData("");
        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write( data,  baos);
        assertEquals("", baos.toString());
    }
}
