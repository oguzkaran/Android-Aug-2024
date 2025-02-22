package org.csystem.app.android.app.counter.data.service

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import java.time.format.DateTimeFormatter

@RunWith(AndroidJUnit4::class)
class CounterDataServiceFindAllTest {
    private var mCounterDataService: CounterDataService? = CounterDataService(mcContext, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))

    companion object {
        private val mcContext = InstrumentationRegistry.getInstrumentation().targetContext

        @BeforeClass
        @JvmStatic
        fun setUp() {
            val counterDataService = CounterDataService(mcContext, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))

            counterDataService.saveSecond(1)
            counterDataService.saveSecond(10)
            counterDataService.saveSecond(60)
            counterDataService.saveSecond(90)
        }
    }

    @Test
    fun findAll_size_test() {
        val expectedSize = 4

        assertEquals(expectedSize, mCounterDataService!!.findAll().size)
    }

    @Test
    fun findAll_seconds_test() {
        val seconds = arrayOf(1L, 10L, 60L, 90L)
        assertArrayEquals(seconds, mCounterDataService!!.findAll().map { it.split("@")[0].toLong() }.toTypedArray())
    }

    @After
    fun tearDown() {
        mCounterDataService = null
    }
}