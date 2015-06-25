package com.minimajack.v8.exeptions;

/**
 * @author e.vanzhula
 *
 */
public class NotValidUUID
    extends RuntimeException
{

    /**
     * @see RuntimeException#RuntimeException()
     */
    public NotValidUUID()
    {
        super();
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable, boolean, boolean)
     */
    public NotValidUUID( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public NotValidUUID( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public NotValidUUID( String message )
    {
        super( message );
    }

    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public NotValidUUID( Throwable cause )
    {
        super( cause );
    }

    /**
     * @see RuntimeException#RuntimeException(String, Exception)
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
