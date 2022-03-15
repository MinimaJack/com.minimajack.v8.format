package com.minimajack.v8;

import com.minimajack.v8.metadata.external.qualifier.Qualifiers;
import com.minimajack.v8.metadata.external.qualifier.QualityTransformer;
import com.minimajack.v8.metadata.external.type.TypeValue;
import com.minimajack.v8.metadata.external.type.TypesTransformer;
import com.minimajack.v8.metadata.inner.classes.V8InnerClass;
import com.minimajack.v8.metadata.inner.classes.transformer.InnerClassTransformer;
import com.minimajack.v8.metadata.style.item.font.FontDescription;
import com.minimajack.v8.metadata.style.item.font.FontTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class TestEnumTransformer {
    private static TypesTransformer transformer;

    @BeforeClass
    public static void init() {
        transformer = new TypesTransformer();
        V8Reader.init();
        V8Reader.registerTransformer(V8InnerClass.class, new InnerClassTransformer());

        V8Reader.registerTransformer(Qualifiers.class, new QualityTransformer());

        V8Reader.registerTransformer(TypeValue.class, new TypesTransformer());

        V8Reader.registerTransformer(FontDescription.class, new FontTransformer());

    }


    @Test
    public void simpleDataWrite() {
        Object s = transformer.read(ByteBuffer.wrap("{\"#\",98ea8e5a-b586-442b-b944-6e3447734aa7,0}".getBytes()));

        SerializedOutputStream baos = new SerializedOutputStream();
        transformer.write(s, baos);
        assertEquals("{\"#\",98ea8e5a-b586-442b-b944-6e3447734aa7,0}", baos.toString());
    }

}
