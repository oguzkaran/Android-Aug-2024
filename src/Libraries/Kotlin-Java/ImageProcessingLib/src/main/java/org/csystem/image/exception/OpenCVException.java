package org.csystem.image.exception;

/**
 * Custom unchecked exception for OpenCV-related errors in the application.
 * @author Oğuz Karan
 */
public class OpenCVException extends RuntimeException {
    /**
     * Constructs a new OpenCVException with {@code null} as its detail message.
     */
    public OpenCVException()
    {
    }

    /**
     * Constructs a new OpenCVException with the specified detail message.
     *
     * @param message the detail message
     */
    public OpenCVException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OpenCVException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public OpenCVException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructs a new OpenCVException with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     * @param enableSuppression whether suppression is enabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    public OpenCVException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}