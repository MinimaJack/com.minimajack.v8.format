package com.minimajack.v8.transformers;

import com.minimajack.v8.transformers.impl.ListTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestListTransformer {
    private static ListTransformer transformer;

    @BeforeClass
    public static void init() {
        V8Reader.init();
        transformer = new ListTransformer();
    }

    @Test
    public void simpleIntegerList()
            throws SecurityException {

        List<?> data = transformer.read(Integer.class, ByteBuffer.wrap("3,1,2,3".getBytes()));
        assertEquals(3, data.size());

    }

    @Test
    public void simpleIntegerListWrite()
            throws SecurityException {
        List<Integer> list = List.of(1,2,3);
        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write(list, baos );

        assertEquals("3,1,2,3", baos.toString());

    }

}
