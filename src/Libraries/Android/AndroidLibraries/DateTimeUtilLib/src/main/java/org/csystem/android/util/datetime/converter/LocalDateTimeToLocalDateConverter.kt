package org.csystem.android.util.datetime.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * A converter class to handle conversion between LocalDateTime and LocalDate.
 * This class is used by Room to convert these types to and from the database.
 *
 * @constructor Creates an instance of LocalDateTimeToLocalDateConverter.
 */
class LocalDateTimeToLocalDateConverter @Inject constructor() {

    /**
     * Converts a LocalDateTime to a LocalDate.
     *
     * @param dateTime The LocalDateTime to be converted.
     * @return The LocalDate representation of the given LocalDateTime.
     */
    @TypeConverter
    fun toLocalDate(dateTime: LocalDateTime): LocalDate = dateTime.toLocalDate()

    /**
     * Converts a LocalDate to a LocalDateTime.
     *
     * @param date The LocalDate to be converted.
     * @return The LocalDateTime representation of the given LocalDate at the start of the day.
     */
    @TypeConverter
    fun toLocalDateTime(date: LocalDate): LocalDateTime = date.atStartOfDay()
}