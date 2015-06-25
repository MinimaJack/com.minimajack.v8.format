package com.minimajack.v8.utility;

/**
 * 
 * @author e.vanzhula
 *
 */
public class AnyData
{

    private String data;

    public AnyData( String data )
    {
        this.data = data;
    }

    public String getData()
    {
        return data;
    }

    public void setData( String data )
    {
        this.data = data;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof AnyData ) )
        {
            return false;
        }
        if ( this.getData() == null )
        {
            return false;
        }
        return this.getData().equals( ( (AnyData) obj ).getData() );
    }

    @Override
    public int hashCode()
    {
        return this.getData().hashCode();
    }
}
