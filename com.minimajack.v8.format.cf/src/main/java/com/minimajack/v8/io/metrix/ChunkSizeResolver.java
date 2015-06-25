package com.minimajack.v8.io.metrix;

import com.minimajack.v8.format.BlockHeader;

public class ChunkSizeResolver
    extends SizeResolver
{
    int chunkSize = 0;

    public ChunkSizeResolver( int chunkSize )
    {
        this.chunkSize = chunkSize;
    }

    public int getChunkSize()
    {
        return chunkSize;
    }

    public void setChunkSize( int chunkSize )
    {
        this.chunkSize = chunkSize;
    }

    @Override
    public int getBufferSize( int streamSize )
    {
        if ( this.chunkSize == 0 )
        {
            return super.getBufferSize( streamSize );
        }
        else
        {
            int countChunks = (int) Math.ceil( (float) streamSize / this.chunkSize );
            return countChunks * ( BlockHeader.V8_HEADERSIZE + chunkSize );
        }
    }

}
