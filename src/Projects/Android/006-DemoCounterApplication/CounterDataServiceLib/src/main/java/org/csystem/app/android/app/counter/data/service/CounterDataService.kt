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
import java.io.File

private const val FILE_NAME = "counter.txt"

/**
 * A service class for managing counter data.
 * This class is a singleton and uses dependency injection for context and date time formatter.
 *
 * @property mContext The application context.
 * @property mDateTimeFormatter The date time formatter.
 * @property mLimit The limit for the number of entries.
 */
@Singleton
class CounterDataService @Inject constructor(
    @ApplicationContext context: Context,
    @DateTimeFormatterTRInterceptor dateTimeFormatter: DateTimeFormatter
) {
    private val mContext = context
    private val mDateTimeFormatter = dateTimeFormatter
    private var mLimit: Int = 10

    companion object {
        const val UNLIMITED = -1
    }
    init {
        val file = File(context.filesDir, FILE_NAME)

        if (!file.exists())
            file.createNewFile()
    }

    private fun countData(): Int {
        if (!File(mContext.filesDir, FILE_NAME).exists())
            throw FileNotFoundException("File not found")

        try {
            BufferedReader(mContext.openFileInput(FILE_NAME).reader(StandardCharsets.UTF_8)).use {
                return generateSequence { it.readLine() }.takeWhile { it != null }.count()
            }
        } catch (ex: IOException) {
            throw DataServiceException("CounterDataService.countData", ex)
        }
    }

    /**
     * Saves the given seconds to the file with the current date and time.
     *
     * @param seconds The seconds to save.
     * @throws DataServiceException If an I/O error occurs.
     */
    fun save(seconds: Long) {
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
    /**
     * Saves the given seconds if the limit is not reached.
     *
     * @param seconds The seconds to save.
     * @return True if the seconds were saved, false otherwise.
     * @throws DataServiceException If an I/O error occurs.
     */
    fun saveSecond(seconds: Long): Boolean {
        var result = true

        try {
            if (mLimit == UNLIMITED || countData() < mLimit) {
                save(seconds)
                result = true
            } else {
                result = false
            }
        } catch (_: FileNotFoundException) {
            save(seconds)
        } catch (ex: IOException) {
            throw DataServiceException("CounterDataService.saveSeconds", ex)
        }

        return result
    }

    /**
     * Removes all data entries from the file.
     *
     * @throws DataServiceException If an I/O error occurs.
     */
    fun removeAll() {
        try {
            mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { }
        } catch (ex: IOException) {
            throw DataServiceException("CounterDataService.removeAll", ex)
        }
    }

    /**
     * Finds all data entries in the file.
     *
     * @return A list of all data entries.
     * @throws DataServiceException If an I/O error occurs.
     */
    fun findAll(): List<String> {
        try {
            BufferedReader(mContext.openFileInput(FILE_NAME).reader(StandardCharsets.UTF_8)).use {
                return generateSequence { it.readLine() }.takeWhile { it != null }.toList()
            }
        } catch (ex: IOException) {
            throw DataServiceException("CounterDataService.findAll", ex)
        }
    }

    /**
     * Sets the limit for the number of data entries.
     *
     * @param limit The limit to set. A value of -1 means no limit.
     * @throws IllegalArgumentException If the limit is less than or equal to 0 and not -1.
     */
    fun setLimit(limit: Int = UNLIMITED) {
        require(limit > 0 || limit == UNLIMITED) { "Invalid limit value" }
        mLimit = limit
    }

    /**
     * Gets the current limit for the number of data entries.
     *
     * @return The current limit.
     */
    fun getLimit() = mLimit
}