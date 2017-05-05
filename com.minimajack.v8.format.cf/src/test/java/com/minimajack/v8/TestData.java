package com.minimajack.v8;

import org.junit.Assert;
import org.junit.Test;

import com.minimajack.v8.format.BlockHeader;
import com.minimajack.v8.io.metrix.ChunkSizeResolver;
import com.minimajack.v8.io.metrix.SizeResolver;

public class TestData
{
 
    @Test
    public void testSizeResolver()
    {
        ChunkSizeResolver chsr = new ChunkSizeResolver( 512 );
        Assert.assertEquals( chsr.getBufferSize( 512 ), 512 + BlockHeader.V8_HEADERSIZE );
        Assert.assertEquals( chsr.getBufferSize( 600 ), 1024 + 2 * BlockHeader.V8_HEADERSIZE );
        Assert.assertEquals( chsr.getBufferSize( 4 ), 512 + BlockHeader.V8_HEADERSIZE );
        SizeResolver sr = new SizeResolver();
        Assert.assertEquals( sr.getBufferSize( 512 ), 512 + BlockHeader.V8_HEADERSIZE );
        Assert.assertEquals( sr.getBufferSize( 600 ), 600 + BlockHeader.V8_HEADERSIZE );
    }


    @Test
    public void testReadLong()
    {
        Assert.assertEquals( 255, Long.parseLong( "FF", 16 ) );
    }

}
