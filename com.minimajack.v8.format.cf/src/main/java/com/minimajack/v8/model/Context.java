package com.minimajack.v8.model;

import java.io.IOException;

import com.minimajack.v8.format.Container;
import com.minimajack.v8.io.reader.AbstractReader;

public class Context
    extends AbstractContextHolder
{
    private String name;

    private boolean inflated = false;

    private Class<? extends AbstractReader> readerClass;

    public Context getParent()
    {
        return this.getContext();
    }

    public Context createChildContext( String name )
    {
        Context child = new Context();
        child.setContext( this );
        child.setName( name );
        return child;
    }

    public void parseContainer( final Container container )
        throws IOException
    {
        AbstractReader reader;
        try
        {
            reader = readerClass.newInstance();
            reader.setContext( this );
            reader.setContainer( container );
            reader.read();
        }
        catch ( InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e )
        {
            throw new RuntimeException( "Exception in reader" );
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name.trim();
    }

    public String getPath()
    {
        if ( this.getContext() != null )
        {
            return this.getContext().getPath() + "/" + name;
        }
        else
        {
            return name;
        }
    }

    public boolean isInflated()
    {
        return inflated;
    }

    public Class<? extends AbstractReader> getReader()
    {
        return readerClass;
    }

    public void setReader( Class<? extends AbstractReader> reader )
    {
        this.readerClass = reader;
    }

    public void setInflated( boolean inflated )
    {
        this.inflated = inflated;
    }

}
