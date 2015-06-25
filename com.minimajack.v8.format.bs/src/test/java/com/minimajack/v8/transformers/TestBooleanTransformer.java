package com.minimajack.v8.transformers;

import java.nio.ByteBuffer;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.minimajack.v8.transformers.impl.BooleanTransformer;
import com.minimajack.v8.utility.V8Reader;

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
    public void simpleFalse()
    {

        Boolean data = transformer.read( ByteBuffer.wrap( "0".getBytes() ) );

        assertEquals( Boolean.FALSE, data );
    }

}
