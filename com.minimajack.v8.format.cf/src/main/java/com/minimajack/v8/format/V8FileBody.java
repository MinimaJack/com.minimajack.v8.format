package com.minimajack.v8.format;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import com.minimajack.v8.io.stream.V8InputStream;
import com.minimajack.v8.utils.V8StreamUtils;

public class V8FileBody
    extends BlockHeader
{
    private InputStream dataStream;

    public V8FileBody()
    {
        super();
    }

    public V8FileBody( ByteBuffer buffer )
    {
        super( buffer );
    }

    public V8FileBody( ByteBuffer buffer, int position )
    {
        super( buffer, position );
    }

    @Override
    public void write( DataOutput buffer )
        throws IOException
    {
        throw new RuntimeException( "Not implemented" );
    }

    @Override
    public void read()
        throws IOException
    {
        if ( this.getDocSize() == 0 )
        {
            return;
        }
        refreshDataStream();
    }

    public void refreshDataStream()
        throws IOException
    {
        if ( getContext().isInflated() )
        {
            dataStream = new InflaterInputStream( this.getInputStream(), new Inflater( true ) );
        }
        else
        {
            V8InputStream v8stream = this.getInputStream();
            v8stream.readExact = true;
            dataStream = v8stream;
        }
    }

    public InputStream getDataStream()
    {
        return dataStream;
    }

    public void setDataStream( InputStream dataStream )
    {
        this.dataStream = dataStream;
    }

    public boolean isContainer()
        throws IOException
    {
        boolean isContainer = this.getDocSize() > ( Container.CONTAINER_SIZE + V8FileAttribute.ATTIBUTES_SIZE )
            && V8StreamUtils.isContainerStream( dataStream );
        this.refreshDataStream();
        return isContainer;
    }

}
