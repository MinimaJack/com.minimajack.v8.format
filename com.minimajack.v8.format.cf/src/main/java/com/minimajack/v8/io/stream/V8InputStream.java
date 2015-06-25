package com.minimajack.v8.io.stream;

import java.io.IOException;
import java.io.InputStream;

import com.minimajack.v8.format.BlockHeader;

public class V8InputStream
    extends InputStream
{

    private Integer rootPosition;

    private BlockHeader currentHeader;

    int fullSize = 0;

    int position = 0;

    int blockAvailable = 0;

    int docAvailable;

    boolean EOF = false;

    byte[] tempBuffer;

    public boolean readExact = false;

    public V8InputStream( BlockHeader iph )
        throws IOException
    {
        this.rootPosition = iph.getPosition();
        this.fullSize = iph.getDocSize();
        this.docAvailable = this.fullSize;
        this.currentHeader = iph;
        if ( this.fullSize == 0 )
        {
            throw new RuntimeException( "Zero size" );
        }
        updateBuffer();
    }

    private void updateBuffer()
    {
        try
        {
            this.position = 0;
            this.currentHeader.readHeader();
            this.blockAvailable = this.currentHeader.getBlockSize();
            this.tempBuffer = this.currentHeader.getRawData();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void reset()
        throws IOException
    {
        this.currentHeader.setPosition( this.rootPosition );
        updateBuffer();
        this.docAvailable = this.fullSize;
    }

    private void getNextData()
        throws IOException
    {
        if ( this.currentHeader.hasNext() )
        {
            this.currentHeader.setPosition( this.currentHeader.getNextBlock() );
            updateBuffer();
        }
        else
        {
            EOF = true;
        }
    }

    @Override
    public int read()
        throws IOException
    {
        if ( EOF )
        {
            return -1;
        }
        if ( this.blockAvailable == 0 )
        {
            getNextData();
            if ( EOF )
            {
                return -1;
            }
        }
        this.blockAvailable--;
        this.docAvailable--;
        return tempBuffer[this.position++] & 0xFF;
    }

    @Override
    public int read( byte[] b )
        throws IOException
    {
        return this.read( b, 0, b.length );
    }

    @Override
    public int read( byte[] b, int off, int len )
        throws IOException
    {
        if ( EOF )
        {
            return -1;
        }
        if ( this.blockAvailable == 0 )
        {
            getNextData();
            if ( EOF )
            {
                return -1;
            }
        }
        int readed = Math.min( this.blockAvailable, len );
        int maxAvailable = Math.min( this.docAvailable, readed );
        if ( readExact )
        {
            readed = maxAvailable;
        }
        System.arraycopy( tempBuffer, this.position, b, off, Math.min( this.docAvailable, readed ) );
        this.position += readed;
        this.docAvailable -= readed;
        this.blockAvailable -= readed;
        if ( readExact && this.docAvailable == 0 )
        {
            EOF = true;
        }
        return readed;
    }

    @Override
    public int available()
        throws IOException
    {
        return this.docAvailable;
    }

}
