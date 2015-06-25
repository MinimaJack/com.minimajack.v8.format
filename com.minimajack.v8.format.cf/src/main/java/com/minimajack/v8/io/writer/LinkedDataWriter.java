package com.minimajack.v8.io.writer;

import java.io.IOException;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.minimajack.v8.model.BufferedObject;

public abstract class LinkedDataWriter
    extends ChunkWriter
{

    final Deque<ChunkWriter> dataToWrite = new ConcurrentLinkedDeque<ChunkWriter>();

    public void writeRaw( BufferedObject bo )
        throws IOException
    {
        bo.write( getDataOutputStream() );
    }

    public void writeRaw( byte[] b )
        throws IOException
    {
        getDataOutputStream().write( b );
    }

    public void attachAdditionData( ChunkWriter chunkWriter )
    {
        this.allocateData( chunkWriter.getMaximumBufferSize() );
        dataToWrite.add( chunkWriter );
    }

    @Override
    protected void onFillBlock()
    {
        try
        {
            while ( dataToWrite.size() > 0 )
            {
                this.writeRaw( dataToWrite.poll().getRawData() );
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
