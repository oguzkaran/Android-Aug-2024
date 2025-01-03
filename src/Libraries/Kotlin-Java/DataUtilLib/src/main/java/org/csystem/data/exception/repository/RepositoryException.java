/*----------------------------------------------------------------
	FILE		: RepositoryException.java
	AUTHOR		: JavaApp1-Nov-2023 Group
	LAST UPDATE	: 22nd June 2024

	RepositoryException class that is used for repository
	pattern

	Copyleft (c) 1993 C and System Programmers Association
	All Rights Free
----------------------------------------------------------------*/
package org.csystem.data.exception.repository;

public class RepositoryException extends RuntimeException {
    public RepositoryException()
    {
        this(null);
    }

    public RepositoryException(String message)
    {
        this(message, null);
    }

    public RepositoryException(String message, Throwable cause)
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
