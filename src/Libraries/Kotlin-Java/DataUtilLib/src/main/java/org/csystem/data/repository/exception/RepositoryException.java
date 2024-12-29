package org.csystem.data.repository.exception;


public class RepositoryException extends RuntimeException {
    public RepositoryException(String message)
    {
        super(message);
    }

    public RepositoryException(String message, Throwable cause)
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
