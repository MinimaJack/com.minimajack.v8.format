package com.minimajack.v8.transformers;

import com.minimajack.v8.transformers.impl.BooleanTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class TestBooleanTransformer
{
    private static BooleanTransformer transformer;

    @BeforeClass
    public static void init()
    {
        V8Reader.init();
        transformer = new BooleanTransformer();
    }

    @Test
    public void simpleTrue()
    {

        Boolean data = transformer.read( ByteBuffer.wrap( "1".getBytes() ) );

        assertEquals( Boolean.TRUE, data );
    }

    @Test
    public void simpleTrueWrite()
    {

        Boolean data = Boolean.TRUE;
        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write( data,  baos);
        assertEquals("1", baos.toString());

        baos = new SerializedOutputStream();
        V8Reader.write( data,  baos);
        assertEquals("1", baos.toString());

    }
    @Test
    public void simpleFalseWrite()
    {

        Boolean data = Boolean.FALSE;
        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write( data,  baos);
        assertEquals("0", baos.toString());

        baos = new SerializedOutputStream();
        V8Reader.write( data,  baos);
        assertEquals("0", baos.toString());
    }

    @Test
    public void simpleFalse()
    {

        Boolean data = transformer.read( ByteBuffer.wrap( "0".getBytes() ) );

        assertEquals( Boolean.FALSE, data );
    }

}
