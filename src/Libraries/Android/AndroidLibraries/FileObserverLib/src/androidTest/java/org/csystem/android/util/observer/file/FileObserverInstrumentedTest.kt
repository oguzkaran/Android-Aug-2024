package org.csystem.android.util.observer.file

import android.content.Context
import android.os.FileObserver
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.nio.charset.StandardCharsets

const val DEFAULT_FILE_LENGTH_LIMIT = "40"
const val DATA_FILE = "data.txt"

@RunWith(AndroidJUnit4::class)
class FileObserverInstrumentedTest {
    private val mAppContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val mDataFileObserver = FileObserverUtil.createFileObserver(mAppContext.filesDir, FileObserver.CLOSE_WRITE) {e, p -> onEventCallback(e, p) }

    private fun onEventCallback(event:Int, path: String?) {
        Log.i("DataFileObserver", "onEventCallback: $path")

        when (event) {
            FileObserver.CLOSE_WRITE -> closeWriteCallback(path, DEFAULT_FILE_LENGTH_LIMIT.toLong())
        }
    }
    private fun closeWriteCallback(path: String?, fileLengthLimit: Long) {
        if (path == null)
            return

        val file = mAppContext.filesDir.resolve(path)
        val length = file.length()
        Log.i("DataFileObserver", "Length: ${file.length()}")

        if (path == DATA_FILE && length > fileLengthLimit) {
            Log.i("DataFileObserver", "File backup is being created")
            mAppContext.deleteFile(DATA_FILE)
            Log.i("DataFileObserver", "File exists:${mAppContext.filesDir.resolve(DATA_FILE).exists()}")
        }
    }

    @Before
    fun setUp() {
        mAppContext.deleteFile(DATA_FILE)
        mDataFileObserver.startWatching()
    }

    @After
    fun tearDown() {
        mDataFileObserver.stopWatching()
    }

    fun writeToFile() {
        mAppContext.openFileOutput(DATA_FILE, Context.MODE_APPEND).use { os ->
            os.write("C and System Programmers Association".toByteArray(StandardCharsets.UTF_8))
            os.flush()
        }
    }

    @Test
    fun testDataFileObserver() {
        writeToFile()
        Thread.sleep(1000)
        Assert.assertTrue(mAppContext.filesDir.resolve(DATA_FILE).exists())
        writeToFile()
        Thread.sleep(1000)
        Assert.assertFalse(mAppContext.filesDir.resolve(DATA_FILE).exists())
    }
}