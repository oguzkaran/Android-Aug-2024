package com.karandev.data.exception.service;

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
