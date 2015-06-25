package com.minimajack.v8.io.metrix;

import com.minimajack.v8.format.BlockHeader;

public class SizeResolver
{

    public int getBufferSize( int streamSize )
    {
        return streamSize + BlockHeader.V8_HEADERSIZE;
    }

}
