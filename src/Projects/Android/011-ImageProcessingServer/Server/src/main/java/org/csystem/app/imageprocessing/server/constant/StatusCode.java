package org.csystem.app.imageprocessing.server.constant;

public final class StatusCode {
    private StatusCode()
    {
    }

    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_FILENAME_DATA_LENGTH_ERROR = 10;
    public static final int STATUS_FILENAME_LENGTH_RECEIVE_ERROR = 13;
    public static final int STATUS_BUFFER_COUNT_LIMIT_RECEIVE_ERROR = 14;
}
