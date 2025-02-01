package org.csystem.app.android.app.counter.data.service

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.csystem.android.library.util.datetime.module.annotation.DateTimeFormatterTRInterceptor
import java.io.BufferedWriter
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val FILE_NAME = "counter.txt"

class CounterDataService @Inject constructor(@ApplicationContext context: Context,
                                             @DateTimeFormatterTRInterceptor dateTimeFormatter: DateTimeFormatter) {
    private val mContext = context
    private val mDateTimeFormatter = dateTimeFormatter

    fun saveSeconds(seconds: Long): Boolean {
        BufferedWriter(mContext.openFileOutput(FILE_NAME, Context.MODE_APPEND).writer(StandardCharsets.UTF_8)).use {
            val nowStr = mDateTimeFormatter.format(LocalDateTime.now())
            it.write("$seconds $nowStr\n")
        }
        return true
    }

    fun removeAll() {
        mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {  }
    }

    fun findSecondsByCount(count: Int): List<Long> {
        TODO("Not yet implemented")
    }
}