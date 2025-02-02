package org.csystem.app.android.app.counter.data.service

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.csystem.android.library.util.datetime.module.annotation.DateTimeFormatterTRInterceptor
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val FILE_NAME = "counter.txt"

class CounterDataService @Inject constructor(@ApplicationContext context: Context,
                                             @DateTimeFormatterTRInterceptor dateTimeFormatter: DateTimeFormatter) {
    private val mContext = context
    private val mDateTimeFormatter = dateTimeFormatter
    private var mLimit: Int = -1
    private fun save(seconds: Long) {
        BufferedWriter(mContext.openFileOutput(FILE_NAME, Context.MODE_APPEND).writer(StandardCharsets.UTF_8)).use {
            val nowStr = mDateTimeFormatter.format(LocalDateTime.now())
            it.write("$seconds@$nowStr\n")
        }
    }

    private fun countData(): Int {
        BufferedReader(mContext.openFileInput(FILE_NAME).reader(StandardCharsets.UTF_8)).use {
            return it.readLines().size
        }
    }

    fun saveSeconds(seconds: Long): Boolean {
        var result = true
        try {
            if (mLimit == -1 || countData() < mLimit) {
                save(seconds)
                result = true
            }
            else
                result = false
        }
        catch (_: FileNotFoundException) {
            save(seconds)
        }

        return result
    }

    fun removeAll() = mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {  }

    fun findAll(): List<Long> {
        TODO("Not yet implemented")
    }

    fun setLimit(limit: Int = -1) {
        if (limit <= 0 && limit != -1)
            throw IllegalArgumentException("Invalid limit value")

        mLimit = limit
    }
}