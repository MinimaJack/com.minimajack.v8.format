package com.minimajack.v8.utility;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.minimajack.v8.transformers.AbstractArraysTransformer;
import com.minimajack.v8.transformers.AbstractClassTransformer;
import com.minimajack.v8.transformers.AbstractEnumTransformer;
import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.transformers.impl.AnyDataTransformer;
import com.minimajack.v8.transformers.impl.ArraysTransformer;
import com.minimajack.v8.transformers.impl.BooleanTransformer;
import com.minimajack.v8.transformers.impl.ClassTransformer;
import com.minimajack.v8.transformers.impl.EnumsTransformer;
import com.minimajack.v8.transformers.impl.IntegerTransformer;
import com.minimajack.v8.transformers.impl.ListTransformer;
import com.minimajack.v8.transformers.impl.MapTransformer;
import com.minimajack.v8.transformers.impl.StringTransformer;
import com.minimajack.v8.transformers.impl.UUIDTransformer;

public class V8Reader
{

    private static final byte SPACE = 0x20;

    private static final byte CR = 0x0D;

    private static final byte LF = 0x0A;

    private static final Map<Class<?>, AbstractTransformer<?>> TRANSFORMERS = new HashMap<>();

    private static final Map<Class<?>, AbstractEnumTransformer<?>> ENUM_TRANSFORMERS = new HashMap<>();

    private static final Map<Class<?>, AbstractArraysTransformer<?>> ARRAYS_TRANSFORMERS = new HashMap<>();

    private static final Map<Class<?>, AbstractClassTransformer<?>> CLASS_TRANSFORMERS = new HashMap<>();

    public static <T> T read( Class<? extends T> clazz, ByteBuffer buffer )
    {
        return read( clazz, null, buffer );
    }

    @SuppressWarnings("unchecked")
    public static <T> T read( Class<T> clazz, Type type, ByteBuffer buffer )
    {
        if ( TRANSFORMERS.containsKey( clazz ) )
        {
            if ( type instanceof ParameterizedType )
            {
                return (T) TRANSFORMERS.get( clazz ).read( (ParameterizedType) type, buffer );
            }
            else
            {
                return (T) TRANSFORMERS.get( clazz ).read( null, buffer );
            }
        }
        if ( clazz.isEnum() )
        {
            return (T) ENUM_TRANSFORMERS.get( Enum.class ).read( clazz, buffer );
        }
        if ( clazz.isArray() )
        {
            return (T) ARRAYS_TRANSFORMERS.get( Arrays.class ).read( clazz, buffer );
        }
        return (T) CLASS_TRANSFORMERS.get( Object.class ).read( clazz, buffer );
    }

    public static final void init()
    {
        TRANSFORMERS.put( String.class, new StringTransformer() );
        TRANSFORMERS.put( Integer.class, new IntegerTransformer() );
        TRANSFORMERS.put( List.class, new ListTransformer() );
        TRANSFORMERS.put( Map.class, new MapTransformer() );
        TRANSFORMERS.put( UUID.class, new UUIDTransformer() );
        TRANSFORMERS.put( Boolean.class, new BooleanTransformer() );
        TRANSFORMERS.put( AnyData.class, new AnyDataTransformer() );

        CLASS_TRANSFORMERS.put( Object.class, new ClassTransformer() );
        ENUM_TRANSFORMERS.put( Enum.class, new EnumsTransformer() );
        ARRAYS_TRANSFORMERS.put( Arrays.class, new ArraysTransformer() );
    }

    public static void registerTransformer( Class<?> clazz, AbstractTransformer<?> transformer )
    {
        TRANSFORMERS.put( clazz, transformer );
    }

    public static void readChar( ByteBuffer buffer, char ch )
    {
        while ( buffer.hasRemaining() )
        {
            int value = buffer.get() & 0xFF;

            if ( (char) value == ch )
            {
                break;
            }
            else if ( value != SPACE && value != CR && value != LF )
            {
                throw new RuntimeException( "Bad chars in buffer [" + String.valueOf(Character.toChars(value))  + "] must be: [" + ch + "]" + " position: "
                    + ( buffer.position() - 1 ) );
            }
        }
    }

}
