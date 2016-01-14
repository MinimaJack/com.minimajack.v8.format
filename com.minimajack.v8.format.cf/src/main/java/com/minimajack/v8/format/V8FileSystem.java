package com.minimajack.v8.format;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import com.minimajack.v8.model.Visitable;
import com.minimajack.v8.model.Visitor;

public class V8FileSystem
    extends BlockHeader
    implements Visitable<V8File>
{

    private LinkedList<V8File> v8FileList = new LinkedList<V8File>();

    private ByteBuffer shadeBuffer = null;

    private int countFiles = 0;

    public V8FileSystem()
    {
        super();
    }

    public V8FileSystem( ByteBuffer buffer )
    {
        this( buffer, buffer.position() );
    }

    public V8FileSystem( ByteBuffer buffer, int position )
    {
        super( buffer, position );
    }

    @Override
    public void read()
        throws IOException
    {
        shadeBuffer = ByteBuffer.wrap( this.readFully() ).order( ByteOrder.LITTLE_ENDIAN );
        countFiles = shadeBuffer.capacity() / V8File.FILE_DESCRIPTION_SIZE;
    }

    @Override
    public void write( DataOutput buffer )
        throws IOException
    {
        throw new RuntimeException( "Not available" );
    }

    public void readFiles()
    {
        for ( int i = 0; i < countFiles; i++ )
        {
            V8File v8File = new V8File( shadeBuffer );
            v8File.setContext( getContext() );
            try
            {
                v8File.read();
                v8File.readHeader( getBuffer() );
                v8File.readBody( getBuffer() );
                v8FileList.add( v8File );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
        shadeBuffer = null;
    }

    public int getCountFiles()
    {
        return countFiles;
    }

    public void setCountFiles( int countFiles )
    {
        this.countFiles = countFiles;
    }

    public List<V8File> getV8FileList()
    {
        return v8FileList;
    }

    public void setV8FileList( LinkedList<V8File> v8FileList )
    {
        this.v8FileList = v8FileList;
    }

    @Override
    public void iterate( Visitor<V8File> visitor )
    {
        for ( V8File v8file : v8FileList )
        {
            visitor.visit( v8file );
        }
    }

    public Stream<V8File> getStream(){
    	return v8FileList.stream();
    }
    
    public void addV8File( V8File v8file )
    {
        v8FileList.add( v8file );
    }

    public V8File findFile( String fileName )
    {

        for ( V8File v8File : v8FileList )
        {
            if ( v8File.getAttributes().getName().equals( fileName ) )
            {
                return v8File;
            }
        }
        return null;
    }

}
