package com.minimajack.v8.format;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import com.google.common.io.ByteStreams;
import com.minimajack.v8.model.BufferedObject;

/**
 * @author e.vanzhula
 *
 * Basic container. Contains other data.
 */
public class Container
    extends BufferedObject
{

    private int freeBlock = Integer.MAX_VALUE;

    private int sizeBlock = 512;

    private int version;

    private int reserved;

    private byte[] data;

    public static final int CONTAINER_SIZE = 4 * 4;

    private V8FileSystem fileSystem;

    public Container()
    {

    }

    public Container( ByteBuffer buffer )
    {
        super( buffer );
    }

    public Container( byte[] data )
    {
        this.data = data;
    }

    @Override
    public void write( DataOutput buffer )
        throws IOException
    {
        buffer.writeInt( this.freeBlock );
        buffer.writeInt( this.sizeBlock );
        buffer.writeInt( this.version );
        buffer.writeInt( this.reserved );
    }

    public void read()
        throws IOException
    {
        if ( !hasBuffer() )
        {
            initBuffer();
        }
        ByteBuffer buffer = getBuffer();
        this.freeBlock = buffer.getInt();
        this.sizeBlock = buffer.getInt();
        this.version = buffer.getInt();
        this.reserved = buffer.getInt();
        fileSystem = new V8FileSystem( buffer );
        fileSystem.setContext( getContext() );
    }

    public void cleanUp()
    {
        getBuffer().clear();
        this.data = null;
    }

    private void initBuffer()
        throws IOException
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream dataStream = new InflaterInputStream( new ByteArrayInputStream( data ), new Inflater( true ) );
        ByteStreams.copy( dataStream, baos );
        ByteBuffer bb = ByteBuffer.wrap( baos.toByteArray() );
        bb.order( ByteOrder.LITTLE_ENDIAN );
        setBuffer( bb );
        this.data = null;
    }

    public long getFreeBlock()
    {
        return freeBlock;
    }

    public void setFreeBlock( int freeBlock )
    {
        this.freeBlock = freeBlock;
    }

    public int getSizeBlock()
    {
        return sizeBlock;
    }

    public void setSizeBlock( int sizeBlock )
    {
        this.sizeBlock = sizeBlock;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion( int version )
    {
        this.version = version;
    }

    public synchronized void updateVersion()
    {
        this.version++;
    }

    public int getReserved()
    {
        return reserved;
    }

    public void setReserved( int reserved )
    {
        this.reserved = reserved;
    }

    public V8FileSystem getFileSystem()
    {
        return fileSystem;
    }

    public void setFileSystem( V8FileSystem fileSystem )
    {
        this.fileSystem = fileSystem;
    }

    @Override
    public int getSize()
        throws IOException
    {
        return CONTAINER_SIZE;
    }
}
