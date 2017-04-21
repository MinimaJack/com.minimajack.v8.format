package com.minimajack.v8.exeptions;

/**
 * @author e.vanzhula
 *
 */
public class NotValidUUID
    extends RuntimeException
{

    public NotValidUUID()
    {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public NotValidUUID( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }

    /**
     * @param message
     * @param cause
     */
    public NotValidUUID( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @param message
     */
    public NotValidUUID( String message )
    {
        super( message );
    }

    /**
     * @param cause
     */
    public NotValidUUID( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param string
     * @param e
     */
    public NotValidUUID( String string, Exception e )
    {
        super( string, e );
    }

    /**
     * 
     */
    private static final long serialVersionUID = 4616071760666080601L;

}
