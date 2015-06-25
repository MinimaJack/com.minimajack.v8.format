package com.minimajack.v8.utils;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BufferUtils
{

    public static final int getLongFromString( ByteBuffer buffer )
        throws IOException
    {
        byte[] stringBuffer = new byte[8];
        buffer.get( stringBuffer );
        buffer.get(); // space
        return (int) Long.parseLong( new String( stringBuffer ), 16 );
    }

    public static final void writeLongToString( DataOutput stream, long value )
        throws IOException
    {
        String formatted = String.format( "%08x ", value );
        stream.write( formatted.getBytes() );
    }
}
