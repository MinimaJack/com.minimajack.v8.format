package com.minimajack.v8.io.writer;

import com.minimajack.v8.format.Container;
import com.minimajack.v8.io.metrix.ChunkSizeResolver;

import java.io.IOException;

public abstract class ContainerWriter
    extends LinkedDataWriter
{

    int fileSystemSize = 0;

    Container container;

    public ContainerWriter()
    {

    }

    public ContainerWriter( int fileSystemSize, Container container )
    {
        this.fileSystemSize = fileSystemSize;
        this.container = container;
    }

    @Override
    public void writeAllData()
    {
        this.setFullSize( fileSystemSize );
        this.setSizeResolver( new ChunkSizeResolver( 512 ) );
        this.allocateData( Container.CONTAINER_SIZE );
        try
        {
            this.writeRaw( container );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        super.writeAllData();
    }

    public int getFileSystemSize()
    {
        return fileSystemSize;
    }

    public void setFileSystemSize( int fileSystemSize )
    {
        this.fileSystemSize = fileSystemSize;
    }

    public Container getContainer()
    {
        return container;
    }

    public void setContainer( Container container )
    {
        this.container = container;
    }

}
