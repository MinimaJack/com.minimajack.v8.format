package com.minimajack.v8.metadata.external.type;

import com.minimajack.v8.annotation.Transformer;

@Transformer(transformer = TypesTransformer.class)
public class TypeValue {

    public Object value;

    public Object getValue() {

        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public <T> T cast() {
        return (T) getValue();
    }
}
