package com.karandev.data.exception.repository;

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
