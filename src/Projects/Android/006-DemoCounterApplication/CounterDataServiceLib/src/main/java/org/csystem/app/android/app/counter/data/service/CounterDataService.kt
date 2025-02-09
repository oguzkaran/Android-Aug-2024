package org.csystem.app.android.app.counter.data.service

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import org.csystem.android.library.util.datetime.module.annotation.DateTimeFormatterTRInterceptor
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton
import com.karandev.data.exception.service.DataServiceException
private const val FILE_NAME = "counter.txt"

@Singleton
class CounterDataService @Inject constructor(@ApplicationContext context: Context,
                                             @DateTimeFormatterTRInterceptor dateTimeFormatter: DateTimeFormatter) {
    private val mContext = context
    private val mDateTimeFormatter = dateTimeFormatter
    private var mLimit: Int = -1

    private fun save(seconds: Long) {
        try {
            BufferedWriter(
                mContext.openFileOutput(FILE_NAME, Context.MODE_APPEND)
                    .writer(StandardCharsets.UTF_8)
            ).use {
                val nowStr = mDateTimeFormatter.format(LocalDateTime.now())
                it.write("$seconds@$nowStr\n")
            }
        } catch (ex: IOException) {
            throw DataServiceException("CounterDataService.save", ex)
        }
    }

    private fun countData(): Int {
        try {
            BufferedReader(mContext.openFileInput(FILE_NAME).reader(StandardCharsets.UTF_8)).use {
                return generateSequence { it.readLine() }.takeWhile { it != null }.count()
            }
        }catch (ex: IOException) {
            throw DataServiceException("CounterDataService.countData", ex)
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
        catch (ex: IOException) {
            throw DataServiceException("CounterDataService.saveSeconds", ex)
        }

        return result
    }

    fun removeAll() {
        try {
            mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { }
        }catch (ex: IOException) {
            throw DataServiceException("CounterDataService.removeAll", ex)
        }
    }

    fun findAll(): List<String> {
        try {
            BufferedReader(mContext.openFileInput(FILE_NAME).reader(StandardCharsets.UTF_8)).use {
                return generateSequence { it.readLine() }.takeWhile { it != null }.toList()
            }
        } catch (ex: IOException) {
            throw DataServiceException("CounterDataService.findAll", ex)
        }
    }

    fun setLimit(limit: Int = -1) {
        if (limit <= 0 && limit != -1)
            throw IllegalArgumentException("Invalid limit value")

        mLimit = limit
    }
}