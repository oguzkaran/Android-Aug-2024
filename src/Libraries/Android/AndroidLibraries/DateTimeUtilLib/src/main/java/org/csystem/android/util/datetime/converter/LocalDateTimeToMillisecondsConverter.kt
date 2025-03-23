package org.csystem.android.util.datetime.converter

import androidx.room.TypeConverter
import org.csystem.util.datetime.converter.DateTimeConvertUtil
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * A converter class to handle conversion between LocalDateTime and milliseconds.
 * This class is used by Room to convert these types to and from the database.
 *
 * @constructor Creates an instance of LocalDateTimeToMillisecondsConverter.
 */
class LocalDateTimeToMillisecondsConverter @Inject constructor() {

    /**
     * Converts a LocalDateTime to milliseconds.
     *
     * @param dateTime The LocalDateTime to be converted.
     * @return The milliseconds representation of the given LocalDateTime.
     */
    @TypeConverter
    fun toMilliseconds(dateTime: LocalDateTime): Long = DateTimeConvertUtil.toMilliseconds(dateTime)

    /**
     * Converts milliseconds to a LocalDateTime.
     *
     * @param milliseconds The milliseconds to be converted.
     * @return The LocalDateTime representation of the given milliseconds.
     */
    @TypeConverter
    fun toLocalDateTime(milliseconds: Long): LocalDateTime = DateTimeConvertUtil.toLocalDateTime(milliseconds)
}