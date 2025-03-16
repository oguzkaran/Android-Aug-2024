package org.csystem.util.datetime.converter;

import java.time.*;

/**
 * Utility class for converting between different date and time representations.
 */
public final class DateTimeConvertUtil {
    private DateTimeConvertUtil()
    {
    }

    /**
     * Converts a LocalDateTime to milliseconds since the epoch.
     *
     * @param localDateTime the LocalDateTime to convert
     * @return the number of milliseconds since the epoch
     */
    public static long toMilliseconds(LocalDateTime localDateTime)
    {
        return toMilliseconds(localDateTime, ZoneId.systemDefault());
    }

    /**
     * Converts a LocalDateTime to milliseconds since the epoch using a specified time zone.
     *
     * @param localDateTime the LocalDateTime to convert
     * @param zoneId the time zone to use for the conversion
     * @return the number of milliseconds since the epoch
     */
    public static long toMilliseconds(LocalDateTime localDateTime, ZoneId zoneId)
    {
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    /**
     * Converts milliseconds since the epoch to a LocalDateTime.
     *
     * @param milliseconds the number of milliseconds since the epoch
     * @return the corresponding LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(long milliseconds)
    {
        return toLocalDateTime(milliseconds, ZoneId.systemDefault());
    }

    /**
     * Converts milliseconds since the epoch to a LocalDateTime using a specified time zone.
     *
     * @param milliseconds the number of milliseconds since the epoch
     * @param zoneId the time zone to use for the conversion
     * @return the corresponding LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(long milliseconds, ZoneId zoneId)
    {
        return Instant.ofEpochMilli(milliseconds).atZone(zoneId).toLocalDateTime();
    }

    /**
     * Converts a LocalDate to milliseconds since the epoch.
     *
     * @param localDate the LocalDate to convert
     * @return the number of milliseconds since the epoch
     */
    public static long toMilliseconds(LocalDate localDate)
    {
        return toMilliseconds(localDate, ZoneId.systemDefault());
    }

    /**
     * Converts a LocalDate to milliseconds since the epoch using a specified time zone.
     *
     * @param localDate the LocalDate to convert
     * @param zoneId the time zone to use for the conversion
     * @return the number of milliseconds since the epoch
     */
    public static long toMilliseconds(LocalDate localDate, ZoneId zoneId)
    {
        return localDate.atStartOfDay(zoneId).toInstant().toEpochMilli();
    }

    /**
     * Converts milliseconds since the epoch to a LocalDate.
     *
     * @param milliseconds the number of milliseconds since the epoch
     * @return the corresponding LocalDate
     */
    public static LocalDate toLocalDate(long milliseconds)
    {
        return toLocalDate(milliseconds, ZoneId.systemDefault());
    }

    /**
     * Converts milliseconds since the epoch to a LocalDate using a specified time zone.
     *
     * @param milliseconds the number of milliseconds since the epoch
     * @param zoneId the time zone to use for the conversion
     * @return the corresponding LocalDate
     */
    public static LocalDate toLocalDate(long milliseconds, ZoneId zoneId)
    {
        return Instant.ofEpochMilli(milliseconds).atZone(zoneId).toLocalDate();
    }
}