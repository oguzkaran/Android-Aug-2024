package org.csystem.android.util.datetime.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

/**
 * A converter class to handle conversion between LocalDateTime and LocalTime.
 * This class is used by Room to convert these types to and from the database.
 *
 * @constructor Creates an instance of LocalDateTimeToLocalTimeConverter.
 */
class LocalDateTimeToLocalTimeConverter @Inject constructor() {

    /**
     * Converts a LocalDateTime to a LocalTime.
     *
     * @param dateTime The LocalDateTime to be converted.
     * @return The LocalTime representation of the given LocalDateTime.
     */
    @TypeConverter
    fun toLocalTime(dateTime: LocalDateTime): LocalTime = dateTime.toLocalTime()

    /**
     * Converts a LocalTime to a LocalDateTime.
     *
     * @param time The LocalTime to be converted.
     * @return The LocalDateTime representation of the given LocalTime on the current date.
     */
    @TypeConverter
    fun toLocalDateTime(time: LocalTime): LocalDateTime = LocalDate.now().atTime(time)
}