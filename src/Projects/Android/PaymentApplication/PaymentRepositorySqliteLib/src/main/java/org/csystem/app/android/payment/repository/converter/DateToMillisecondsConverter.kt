package org.csystem.app.android.payment.repository.converter

import androidx.room.TypeConverter
import org.csystem.util.datetime.converter.DateTimeConvertUtil
import java.time.LocalDate
import javax.inject.Inject

class DateToMillisecondsConverter @Inject constructor() {
    @TypeConverter
    fun toMilliseconds(date: LocalDate): Long = DateTimeConvertUtil.toMilliseconds(date)

    @TypeConverter
    fun toLocalDate(milliseconds: Long): LocalDate = DateTimeConvertUtil.toLocalDate(milliseconds)
}