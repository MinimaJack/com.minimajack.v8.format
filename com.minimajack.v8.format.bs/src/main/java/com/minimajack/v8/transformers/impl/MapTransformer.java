package com.minimajack.v8.transformers.impl;

import com.minimajack.v8.transformers.AbstractParametrizedTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapTransformer
        implements AbstractParametrizedTransformer<Map<?, ?>> {

    @Override
    public Map<?, ?> read(ParameterizedType type, ByteBuffer buffer) {
        Type[] types = type.getActualTypeArguments();
        Class<?> firstClass = (Class<?>) types[0];
        Class<?> secondClass = (Class<?>) types[1];
        return read(firstClass, secondClass, buffer);
    }

    @Override
    public void write(Object object, SerializedOutputStream buffer) {
        Map<Object, Object> map = (Map<Object, Object>) object;
        Integer count = map.size();
        V8Reader.write(count, buffer);
        for (var element : map.entrySet()) {
            buffer.putComa();
            V8Reader.write(element.getKey(), buffer);
            buffer.putComa();
            V8Reader.write(element.getValue(), buffer);
        }
    }

    public Map<?, ?> read(Class<?> keyClass, Class<?> valueClass, ByteBuffer buffer) {
        HashMap<Object, Object> map = new LinkedHashMap<>();

        Integer count = V8Reader.read(Integer.class, buffer);
        for (int i = 0; i < count; i++) {
            V8Reader.readChar(buffer, ',');
            Object key = V8Reader.read(keyClass, buffer);
            V8Reader.readChar(buffer, ',');
            Object value = V8Reader.read(valueClass, buffer);
            map.put(key, value);
        }
        return map;
    }
}
