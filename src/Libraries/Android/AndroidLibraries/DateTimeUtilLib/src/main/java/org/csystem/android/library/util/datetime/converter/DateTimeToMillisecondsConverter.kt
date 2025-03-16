package org.csystem.android.library.util.datetime.converter

import androidx.room.TypeConverter
import org.csystem.util.datetime.converter.DateTimeConvertUtil
import java.time.LocalDateTime
import javax.inject.Inject

class DateTimeToMillisecondsConverter @Inject constructor() {
    @TypeConverter
    fun toMilliseconds(dateTime: LocalDateTime): Long = DateTimeConvertUtil.toMilliseconds(dateTime)

    @TypeConverter
    fun toLocalDateTime(milliseconds: Long): LocalDateTime = DateTimeConvertUtil.toLocalDateTime(milliseconds)
}