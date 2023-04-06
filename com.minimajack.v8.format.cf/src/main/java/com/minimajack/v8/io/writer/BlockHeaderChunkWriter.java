package com.minimajack.v8.io.writer;

import com.minimajack.v8.format.BlockHeader;
import com.minimajack.v8.io.metrix.ChunkSizeResolver;

import java.io.IOException;

public class BlockHeaderChunkWriter
    extends ChunkWriter
{

    private BlockHeader bo;

    private boolean written = false;

    public BlockHeaderChunkWriter( BlockHeader bo )
    {
        this.bo = bo;
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
            this.write( bo );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setSizeResolver( ChunkSizeResolver sizeResolver )
    {
        super.setSizeResolver( sizeResolver );
        this.bo.setBlockSize( sizeResolver.getChunkSize() );
    }

    @Override
    protected void writeData()
    {
        writeAllData();
    }

}
