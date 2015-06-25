package com.minimajack.v8.utils;

import java.io.IOException;
import java.io.InputStream;

public class V8StreamUtils
{
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static boolean isContainerStream( InputStream is )
        throws IOException
    {
        return is.read() == 0xFF && is.read() == 0xFF && is.read() == 0xFF && is.read() == 0x7F;
    }

    public static String bytesToHex( byte[] bytes )
    {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String( hexChars );
    }
}
