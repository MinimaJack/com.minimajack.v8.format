package com.minimajack.v8.utility;

import com.minimajack.v8.transformers.AbstractClassTransformer;
import com.minimajack.v8.transformers.AbstractParametrizedTransformer;
import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.transformers.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.*;

public class V8Reader {

    final static Logger logger = LoggerFactory.getLogger( V8Reader.class );

    private static final byte SPACE = 0x20;

    private static final byte CR = 0x0D;

    private static final byte LF = 0x0A;

    private static final Map<Class<?>, AbstractTransformer<?>> TRANSFORMERS = new HashMap<>();

    private static final Map<Class<?>, AbstractParametrizedTransformer<?>> PARAMETERIZED_TRANSFORMERS = new HashMap<>();

    private static final Map<Class<?>, AbstractClassTransformer<?>> CLASS_TRANSFORMERS = new HashMap<>();

    public static <T> T read(Class<? extends T> clazz, ByteBuffer buffer) {
        return read(clazz, null, buffer);
    }

    @SuppressWarnings("unchecked")
    public static <T> T read(Class<T> clazz, Type type, ByteBuffer buffer) {
        logger.debug("Read class {}", clazz.getName());
        T res = null;
        if (TRANSFORMERS.containsKey(clazz)) {
            if (type instanceof ParameterizedType) {
               throw new RuntimeException("Must not be here");
            } else {
                res = (T) TRANSFORMERS.get(clazz).read(buffer);
            }
        } else if(PARAMETERIZED_TRANSFORMERS.containsKey(clazz)){
            if (PARAMETERIZED_TRANSFORMERS.containsKey(clazz)) {
                if (type instanceof ParameterizedType) {
                    res = (T) PARAMETERIZED_TRANSFORMERS.get(clazz).read((ParameterizedType) type, buffer);
                } else {
                    throw new RuntimeException("Must not be here");
                }
            }
        }
        else if (clazz.isEnum()) {
            res = (T) CLASS_TRANSFORMERS.get(Enum.class).read(clazz, buffer);
        } else if (clazz.isArray()) {
            res = (T) CLASS_TRANSFORMERS.get(Arrays.class).read(clazz, buffer);
        } else {
            res = (T) CLASS_TRANSFORMERS.get(Object.class).read(clazz, buffer);
        }
        logger.debug("End read class {}", clazz.getName());
        return res;

    }


    public static void write(Object object, SerializedOutputStream buffer) {
       write(object, null, buffer);
    }
    public static void write(Object object, Type type, SerializedOutputStream buffer) {
        Class<?> clazz = object.getClass();
        if (TRANSFORMERS.containsKey(clazz)) {
            TRANSFORMERS.get(clazz).write(object, buffer);
        }  else if(PARAMETERIZED_TRANSFORMERS.containsKey(clazz)){
             PARAMETERIZED_TRANSFORMERS.get(clazz).write(object, buffer);
        }else if (clazz.isEnum()) {
            CLASS_TRANSFORMERS.get(Enum.class).write(object, buffer);
        } else if (clazz.isArray()) {
            CLASS_TRANSFORMERS.get(Arrays.class).write(object, buffer);
        } else {
            CLASS_TRANSFORMERS.get(Object.class).write(object, buffer);
        }
    }

    public static void init() {
        TRANSFORMERS.put(String.class, new StringTransformer());
        TRANSFORMERS.put(Integer.class, new IntegerTransformer());
        TRANSFORMERS.put(UUID.class, new UUIDTransformer());
        TRANSFORMERS.put(Boolean.class, new BooleanTransformer());
        TRANSFORMERS.put(AnyData.class, new AnyDataTransformer());

        PARAMETERIZED_TRANSFORMERS.put(List.class, new ListTransformer());
        PARAMETERIZED_TRANSFORMERS.put(ArrayList.class, new ListTransformer());
        PARAMETERIZED_TRANSFORMERS.put(LinkedList.class, new ListTransformer());

        PARAMETERIZED_TRANSFORMERS.put(Map.class, new MapTransformer());
        PARAMETERIZED_TRANSFORMERS.put(LinkedHashMap.class, new MapTransformer());

        CLASS_TRANSFORMERS.put(Object.class, new ObjectTransformer());
        CLASS_TRANSFORMERS.put(Enum.class, new EnumsTransformer());
        CLASS_TRANSFORMERS.put(Arrays.class, new ArraysTransformer());
    }

    public static void registerTransformer(Class<?> clazz, AbstractTransformer<?> transformer) {
        TRANSFORMERS.put(clazz, transformer);
    }

    public static void registerParametrizedTransformer(Class<?> clazz, AbstractParametrizedTransformer<?> transformer) {
        PARAMETERIZED_TRANSFORMERS.put(clazz, transformer);
    }

    public static void readChar(ByteBuffer buffer, char ch) {
        while (buffer.hasRemaining()) {
            int value = buffer.get() & 0xFF;

            if ((char) value == ch) {
                break;
            } else if (value != SPACE && value != CR && value != LF) {
                throw new RuntimeException("Bad chars in buffer [" + String.valueOf(Character.toChars(value)) + "] must be: [" + ch + "]" + " position: "
                        + (buffer.position() - 1));
            }
        }
    }

    public static char nextChar(ByteBuffer buffer) {
        buffer.mark();
        char result = 0;
        while (buffer.hasRemaining()) {
            int value = buffer.get() & 0xFF;

            if (value != SPACE && value != CR && value != LF) {
                logger.debug("Next char {}", (char) value);
                result = ((char) value);
                break;
            }
        }
        buffer.reset();
        return result;
    }

}
