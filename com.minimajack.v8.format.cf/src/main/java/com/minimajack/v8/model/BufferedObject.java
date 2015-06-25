package com.minimajack.v8.model;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class BufferedObject
    extends AbstractContextHolder
{
    private ByteBuffer buffer;

    private int position;

    public BufferedObject()
    {

    }

    public BufferedObject( ByteBuffer buffer )
    {
        this( buffer, buffer.position() );
    }

    public BufferedObject( ByteBuffer buffer, int position )
    {
        this.buffer = buffer;
        this.position = position;
    }

    public boolean hasBuffer()
    {
        return this.buffer != null;
    }

    public ByteBuffer getBuffer()
    {
        return buffer;
    }

    public void setBuffer( ByteBuffer buffer )
    {
        this.buffer = buffer;
    }

    public void setStartPosition()
        throws IOException
    {
        getBuffer().position( getPosition() );
    }

    public abstract void write( DataOutput dos )
        throws IOException;

    public abstract int getSize()
        throws IOException;

    public int getPosition()
    {
        return position;
    }

    public void setPosition( int position )
    {
        this.position = position;
    }

}
