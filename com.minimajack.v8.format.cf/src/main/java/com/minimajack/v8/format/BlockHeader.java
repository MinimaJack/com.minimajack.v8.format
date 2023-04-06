package com.minimajack.v8.format;

import com.google.common.io.ByteStreams;
import com.minimajack.v8.io.stream.V8InputStream;
import com.minimajack.v8.model.BufferedObject;
import com.minimajack.v8.utils.BufferUtils;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author e.vanzhula
 * 
 */
public class BlockHeader
    extends BufferedObject
{
    /**
     * Full size for first block or zero for the next
     */
    private int payloadSize;

    /**
     * Block size - without header size
     */
    private int blockSize;

    /**
     * Next block address or Integer.MAX_VALUE if next block does not exist
     */
    private int nextBlockPosition = Integer.MAX_VALUE;

    /**
     * temporary flag to read header
     * 
     * TODO:remove unneeded operation
     */
    private boolean ready = false;

    /**
     * Basic v8 marker
     */
    public static final short V8_SEPARATOR = 0x0A0D;

    /**
     * End block marker
     */
    public static final int V8_ENDBLOCK = Integer.MAX_VALUE;

    /**
     * Full header size
     */
    public static final int V8_HEADERSIZE = 2 + 9 + 9 + 9 + 2;

    public BlockHeader()
    {
        setReady( true );
    }

    public BlockHeader( ByteBuffer buffer )
    {
        super( buffer );
    }

    public BlockHeader( ByteBuffer buffer, int position )
    {
        super( buffer, position );
    }

    public boolean hasNext()
        throws IOException
    {
        return !this.getNextBlock().equals( Integer.MAX_VALUE );
    }

    public void read()
        throws IOException
    {
        readHeader();
    }

    @Override
    public void write( DataOutput dos )
        throws IOException
    {
        dos.writeShort( V8_SEPARATOR );
        BufferUtils.writeLongToString( dos, this.payloadSize );
        BufferUtils.writeLongToString( dos, this.blockSize );
        BufferUtils.writeLongToString( dos, this.nextBlockPosition );
        dos.writeShort( V8_SEPARATOR );
    }

    /**
     * Read data-stream to byte array
     * 
     * @return data
     * @throws IOException
     *             if can't read full data or stream broken
     */
    public byte[] readFully()
        throws IOException
    {
        byte[] data = new byte[this.getDocSize()];
        ByteStreams.readFully( new V8InputStream( this ), data );
        return data;
    }

    public void reset()
        throws IOException
    {
        this.readHeader();
    }

    public boolean isReady()
    {
        return ready;
    }

    public void setReady( boolean ready )
    {
        this.ready = ready;
    }

    public void readHeader()
        throws IOException
    {
        this.setStartPosition();
        ByteBuffer buffer = getBuffer();
        buffer.getShort();
        this.payloadSize = BufferUtils.getLongFromString( buffer );
        this.blockSize = BufferUtils.getLongFromString( buffer );
        this.nextBlockPosition = BufferUtils.getLongFromString( buffer );
        buffer.getShort();
        this.ready = true;
    }

    public V8InputStream getInputStream()
        throws IOException
    {
        return new V8InputStream( this );
    }

    public byte[] getRawData()
        throws IOException
    {
        byte[] rawdata = new byte[this.getBlockSize()];
        getBuffer().get( rawdata );
        return rawdata;
    }

    public byte[] getDocData()
        throws IOException
    {
        this.readHeader();
        if ( this.getDocSize() > this.getBlockSize() )
        {
            throw new RuntimeException( "Document size greather then block in attributes" );
        }
        byte[] rawdata = new byte[this.getDocSize()];
        getBuffer().get( rawdata );
        return rawdata;
    }

    public int getDocSize()
        throws IOException
    {
        if ( !this.ready )
        {
            this.readHeader();
        }
        return payloadSize;
    }

    public void setDocSize( int docSize )
    {
        this.payloadSize = docSize;
    }

    public int getBlockSize()
        throws IOException
    {
        if ( !this.ready )
        {
            this.readHeader();
        }
        return blockSize;
    }

    public void setBlockSize( int blockSize )
    {
        this.blockSize = blockSize;
    }

    public Integer getNextBlock()
        throws IOException
    {
        if ( !this.ready )
        {
            this.readHeader();
        }
        return nextBlockPosition;
    }

    public void setNextBlock( int nextBlock )
    {
        this.nextBlockPosition = nextBlock;
    }

    @Override
    public int getSize()
        throws IOException
    {
        throw new RuntimeException( "Can't get real size" );
    }

}
