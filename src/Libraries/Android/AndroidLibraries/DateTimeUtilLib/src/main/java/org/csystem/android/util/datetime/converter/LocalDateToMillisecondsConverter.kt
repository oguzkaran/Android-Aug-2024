package org.csystem.android.util.datetime.converter

import androidx.room.TypeConverter
import org.csystem.util.datetime.converter.DateTimeConvertUtil
import java.time.LocalDate
import javax.inject.Inject

/**
 * A converter class to handle conversion between LocalDate and milliseconds.
 * This class is used by Room to convert these types to and from the database.
 *
 * @constructor Creates an instance of LocalDateToMillisecondsConverter.
 */
class LocalDateToMillisecondsConverter @Inject constructor() {

    /**
     * Converts a LocalDate to milliseconds.
     *
     * @param date The LocalDate to be converted.
     * @return The milliseconds representation of the given LocalDate.
     */
    @TypeConverter
    fun toMilliseconds(date: LocalDate): Long = DateTimeConvertUtil.toMilliseconds(date)

    /**
     * Converts milliseconds to a LocalDate.
     *
     * @param milliseconds The milliseconds to be converted.
     * @return The LocalDate representation of the given milliseconds.
     */
    @TypeConverter
    fun toLocalDate(milliseconds: Long): LocalDate = DateTimeConvertUtil.toLocalDate(milliseconds)
}