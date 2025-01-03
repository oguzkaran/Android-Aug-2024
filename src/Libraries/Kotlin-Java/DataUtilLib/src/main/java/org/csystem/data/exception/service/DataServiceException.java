/*----------------------------------------------------------------
	FILE		: DataServiceException.java
	AUTHOR		: JavaApp1-Nov-2023 Group
	LAST UPDATE	: 22nd June 2024

	DataServiceException class that is used for repository
	pattern

	Copyleft (c) 1993 C and System Programmers Association
	All Rights Free
----------------------------------------------------------------*/
package org.csystem.data.exception.service;

public class DataServiceException extends RuntimeException {
    public DataServiceException()
    {
        this(null);
    }

    public DataServiceException(String message)
    {
        this(message, null);
    }

    public DataServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    @Override
    public String getMessage()
    {
        var cause = getCause();

        return String.format("Message:%s%s", super.getMessage(),
                cause != null ? ", Cause Message:" + cause.getMessage() : "");
    }
}
