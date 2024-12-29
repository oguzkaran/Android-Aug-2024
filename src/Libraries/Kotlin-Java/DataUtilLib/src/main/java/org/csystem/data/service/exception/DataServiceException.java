package org.csystem.data.service.exception;


public class DataServiceException extends RuntimeException {
    public DataServiceException(String message)
    {
        super(message);
    }

    public DataServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    @Override
    public String getMessage()
    {
        var cause = getCause();

        return String.format("Message:%s%s", super.getMessage(), cause != null ? ", Cause Message: " + cause.getMessage() : "");
    }
}
