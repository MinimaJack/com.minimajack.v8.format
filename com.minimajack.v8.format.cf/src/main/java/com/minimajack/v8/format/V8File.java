package com.minimajack.v8.format;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.minimajack.v8.model.BufferedObject;

public class V8File
    extends BufferedObject
{

    public static final int FILE_DESCRIPTION_SIZE = 12;

    private int headerAddress = BlockHeader.V8_ENDBLOCK;

    private int bodyAddress = BlockHeader.V8_ENDBLOCK;

    private V8FileAttribute attributes;

    private V8FileBody body;

    public V8File()
    {

    }

    public V8File( ByteBuffer buffer )
    {
        super( buffer );
    }

    public void read()
        throws IOException
    {
        setStartPosition();
        ByteBuffer buffer = getBuffer();
        this.headerAddress = buffer.getInt() & 0xFFFFFFFF;
        this.bodyAddress = buffer.getInt() & 0xFFFFFFFF;
        int reserved = buffer.getInt() & 0xFFFFFFFF;
        if ( reserved != Integer.MAX_VALUE )
        {
            throw new RuntimeException( "Bad magic number" );
        }
    }

    @Override
    public void write( DataOutput buffer )
        throws IOException
    {
        buffer.writeInt( this.headerAddress );
        buffer.writeInt( this.bodyAddress );
        buffer.writeInt( Integer.MAX_VALUE );
    }

    public boolean isContainer()
        throws IOException
    {
        return this.body != null && this.body.isContainer();
    }

    public void readHeader( ByteBuffer dataBufer )
        throws IOException
    {
        if ( this.headerAddress != BlockHeader.V8_ENDBLOCK )
        {
            this.attributes = new V8FileAttribute( dataBufer, this.headerAddress );
            this.attributes.setContext( getContext() );
            this.attributes.read();
        }
    }

    public void readBody( ByteBuffer dataBufer )
        throws IOException
    {
        if ( this.headerAddress != BlockHeader.V8_ENDBLOCK && this.bodyAddress != BlockHeader.V8_ENDBLOCK )
        {
            this.body = new V8FileBody( dataBufer, this.bodyAddress );
            this.body.setContext( getContext() );
            this.body.read();
        }
    }

    public V8FileAttribute getAttributes()
    {
        return attributes;
    }

    public void setAttributes( V8FileAttribute attributes )
    {
        this.attributes = attributes;
        this.headerAddress = attributes.getPosition();
    }

    public V8FileBody getBody()
    {
        return body;
    }

    public void setBody( V8FileBody body )
    {
        this.body = body;
        this.bodyAddress = body.getPosition();
    }

    public int getHeaderAddress()
    {
        return headerAddress;
    }

    public void setHeaderAddress( int headerAddress )
    {
        this.headerAddress = headerAddress;
    }

    public int getBodyAddress()
    {
        return bodyAddress;
    }

    public void setBodyAddress( int bodyAddress )
    {
        this.bodyAddress = bodyAddress;
    }

    @Override
    public int getSize()
        throws IOException
    {
        return FILE_DESCRIPTION_SIZE;
    }

}
