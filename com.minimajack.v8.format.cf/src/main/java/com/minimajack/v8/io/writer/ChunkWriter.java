package com.minimajack.v8.io.writer;

import com.google.common.io.LittleEndianDataOutputStream;
import com.minimajack.v8.format.BlockHeader;
import com.minimajack.v8.io.metrix.ChunkSizeResolver;
import com.minimajack.v8.io.metrix.SizeResolver;
import com.minimajack.v8.model.BufferedObject;
import com.minimajack.v8.model.FakeMemoryAllocator;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.IOException;

public abstract class ChunkWriter
{

    private final ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();

    private final LittleEndianDataOutputStream dataOutputStream = new LittleEndianDataOutputStream( outputBuffer );

    private final FakeMemoryAllocator allocator = new FakeMemoryAllocator();

    private final BlockHeader cursor = new BlockHeader();

    private ByteArrayOutputStream chunkBuffer;

    private LittleEndianDataOutputStream chunkBufferOutputStream;

    private ChunkSizeResolver sizeResolver;

    private int position = 0;

    private int limit;

    public ChunkWriter()
    {
        super();
    }

    public void write( BufferedObject bo )
        throws IOException
    {
        bo.write( chunkBufferOutputStream );
        flushChunks();
    }

    public void write( byte[] b )
        throws IOException
    {
        chunkBuffer.write( b );
        flushChunks();
    }

    private void flushChunks()
    {
        position = chunkBuffer.size();
        if ( position > limit )
        {
            int offset = 0;
            byte[] data = chunkBuffer.toByteArray();
            while ( position > limit )
            {
                int sizeToWrite = ( position / limit ) * limit;
                while ( sizeToWrite > offset )
                {
                    offset += dataReady( data, offset, limit );
                }
                position -= offset;
            }
            chunkBuffer.reset();
            chunkBuffer.write( data, offset, data.length - offset );
        }
    }

    public void allocateData( int size )
    {
        allocator.allocate( size );
    }

    public int getPosition()
    {
        return allocator.getAllocationSize();
    }

    public void writeAllData()
    {
        this.allocateData( this.sizeResolver.getChunkSize() + BlockHeader.V8_HEADERSIZE );
        while ( hasData() )
        {
            getDataToWrite();
        }
        flush();
    }

    public abstract void getDataToWrite();

    public boolean hasData()
    {
        return position > limit;
    }

    public void flush()
    {
        byte[] data = new byte[limit];
        byte[] tempData = chunkBuffer.toByteArray();
        for ( int i = 0; i < tempData.length; i++ )
        {
            data[i] = tempData[i];
        }
        fillChunk( data, 0, limit );
    }

    private void fillChunk( byte[] b, int off, int len )
    {
        try
        {
            cursor.write( dataOutputStream );
            dataOutputStream.write( b, off, len );
            cursor.setDocSize( 0 );
            cursor.setNextBlock( Integer.MAX_VALUE );
            onFillBlock();
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Can't write readed data" );
        }
    }

    protected void onFillBlock()
    {

    }

    private int dataReady( byte[] b, int off, int len )
    {
        if ( this.hasData() )
        {
            this.cursor.setNextBlock( getPosition() );
        }
        fillChunk( b, off, len );
        this.allocateData( sizeResolver.getChunkSize() + BlockHeader.V8_HEADERSIZE );
        return len;
    }

    public SizeResolver getSizeResolver()
    {
        return sizeResolver;
    }

    public void setFullSize( int fullSize )
    {
        this.cursor.setDocSize( fullSize );
    }

    public void setSizeResolver( ChunkSizeResolver sizeResolver )
    {
        this.sizeResolver = sizeResolver;
        this.limit = this.sizeResolver.getChunkSize();
        this.chunkBuffer = new ByteArrayOutputStream( this.limit );
        this.chunkBufferOutputStream = new LittleEndianDataOutputStream( chunkBuffer );
        this.cursor.setBlockSize( this.limit );
    }

    public byte[] getRawData()
    {
        return outputBuffer.toByteArray();
    }

    public DataOutput getDataOutputStream()
    {
        return dataOutputStream;
    }

    public int getMaximumBufferSize()
    {
        if ( outputBuffer.size() == 0 )
        {
            writeAllData();
        }
        return outputBuffer.size();
    }

    protected void writeData()
    {

    }

}