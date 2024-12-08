package org.csystem.data.exception;

import androidx.annotation.Nullable;

public class DataServiceException extends RuntimeException {
    public DataServiceException(String message)
    {
        super(message);
    }

    public DataServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    @Nullable
    @Override
    public String getMessage()
    {
        var cause = getCause();

        return String.format("Message:%s%s", super.getMessage(), cause != null ? ", Cause Message: " + cause.getMessage() : "");
    }
}
