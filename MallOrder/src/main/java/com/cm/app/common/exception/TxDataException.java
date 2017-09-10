package com.cm.app.common.exception;

public class TxDataException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public TxDataException( String cause )
    {
        super( cause );
    }

    public TxDataException( String cause, Throwable t )
    {
        super( cause, t );
    }
}
