package com.minimajack.v8.transformers;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.transformers.impl.ObjectTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TestObjectTransformer {
    private static ObjectTransformer transformer;

    @BeforeClass
    public static void init() {
        transformer = new ObjectTransformer();
        V8Reader.init();

    }

    @Test
    public void simpleData() {
        TestClass example = new TestClass();
        example.un1 = 1;

        var data = transformer.read(TestObjectTransformer.TestClass.class, ByteBuffer.wrap("1".getBytes()));
        assertEquals(example, data);
    }

    @Test
    public void simpleDataWrite() {
        TestClass example = new TestClass();
        example.un1 = 1;

        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write(example, baos);
        assertEquals("1", baos.toString());
    }

    @Test
    public void simpleDataV8() {
        TestClassV8 example = new TestClassV8();
        example.un1 = 1;

        var data = transformer.read(TestObjectTransformer.TestClassV8.class, ByteBuffer.wrap("{1}".getBytes()));
        assertEquals(example, data);
    }

    @Test
    public void simpleDataV8Write() {
        TestClassV8 example = new TestClassV8();
        example.un1 = 1;

        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write(example, baos);
        assertEquals("{1}", baos.toString().strip());
    }

    @Test
    public void simpleTwoDataV8Read() {
        TestClassV8Multiple expected = new TestClassV8Multiple();
        expected.setUn1(1);
        expected.setUn2(UUID.fromString("2bcef0d1-0981-11d6-b9b8-0050bae0a95d"));

        Object actual = transformer.read(TestClassV8Multiple.class, ByteBuffer.wrap("{1,2bcef0d1-0981-11d6-b9b8-0050bae0a95d}".getBytes()));
        assertEquals(expected, actual);
    }

    @Test
    public void simpleTwoDataV8Write() {
        TestClassV8Multiple example = new TestClassV8Multiple();
        example.un1 = 1;
        example.un2 = UUID.fromString("2bcef0d1-0981-11d6-b9b8-0050bae0a95d");

        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write(example, baos);
        assertEquals("{1,2bcef0d1-0981-11d6-b9b8-0050bae0a95d}", baos.toString());
    }

    @Test
    public void simpleV8Synonym() {
        V8Synonym synonym = new V8Synonym();
        synonym.sinonyms = new LinkedHashMap<>();
        synonym.sinonyms.put("ru", "Клиент ДепФин 3.0");

        SerializedOutputStream baos = new SerializedOutputStream();
        V8Reader.write(synonym, baos);
        assertEquals("1,\"ru\",\"Клиент ДепФин 3.0\"", baos.toString());
    }


    public static class V8Synonym {
        public Map<String, String> sinonyms;
    }

    public static class TestClass {
        public Integer un1;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestClass)) return false;
            TestClass testClass = (TestClass) o;
            return Objects.equals(un1, testClass.un1);
        }

        @Override
        public int hashCode() {
            return Objects.hash(un1);
        }
    }

    @V8Class
    public static class TestClassV8 {
        public Integer un1;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestClassV8)) return false;
            TestClassV8 testClass = (TestClassV8) o;
            return Objects.equals(un1, testClass.un1);
        }

        @Override
        public int hashCode() {
            return Objects.hash(un1);
        }
    }

    @V8Class
    public static class TestClassV8Multiple {

        private Integer un1;
        private UUID un2;

        public Integer getUn1() {
            return un1;
        }

        public void setUn1(Integer un1) {
            this.un1 = un1;
        }

        public UUID getUn2() {
            return un2;
        }

        public void setUn2(UUID un2) {
            this.un2 = un2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestClassV8Multiple)) return false;
            TestClassV8Multiple that = (TestClassV8Multiple) o;
            return Objects.equals(un1, that.un1) && Objects.equals(un2, that.un2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(un1, un2);
        }
    }

}
