package com.minimajack.v8.io.writer;

import java.io.IOException;

public class RawChunkWriter
    extends ChunkWriter
{

    private byte[] data;

    private boolean written = false;

    public RawChunkWriter( byte[] b )
    {
        this.data = b;
        this.setFullSize( b.length );
    }

    @Override
    public boolean hasData()
    {
        return !written || super.hasData();
    }

    @Override
    public void getDataToWrite()
    {
        try
        {
            written = true;
            this.write( this.data );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void writeData()
    {
        writeAllData();
    }

}
