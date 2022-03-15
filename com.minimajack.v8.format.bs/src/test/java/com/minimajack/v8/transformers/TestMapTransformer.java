package com.minimajack.v8.transformers;

import com.minimajack.v8.transformers.impl.MapTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void simpleIntegerMapWrite()
            throws SecurityException {
        Map<Integer,Integer> map = new LinkedHashMap<>();
        map.put(1,2);
        map.put(3,1);
        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write(map, baos );

        assertEquals("2,1,2,3,1", baos.toString());

    }
}
