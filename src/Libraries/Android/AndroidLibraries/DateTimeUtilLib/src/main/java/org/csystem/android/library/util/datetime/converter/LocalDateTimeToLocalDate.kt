package org.csystem.android.library.util.datetime.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class LocalDateTimeToLocalDate @Inject constructor() {
    @TypeConverter
    fun toLocalDate(dateTime: LocalDateTime): LocalDate = dateTime.toLocalDate()

    @TypeConverter
    fun toLocalDateTime(date: LocalDate): LocalDateTime = date.atStartOfDay()
}