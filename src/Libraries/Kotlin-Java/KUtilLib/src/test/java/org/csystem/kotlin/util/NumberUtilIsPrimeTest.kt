package org.csystem.kotlin.util

import org.csystem.kotlin.util.numeric.isPrime
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.BufferedReader
import java.io.FileReader
import java.nio.charset.StandardCharsets
import kotlin.test.Test
import kotlin.test.assertEquals

private const val PATH = "testdata/isPrimeData"
@RunWith(Parameterized::class)
class NumberUtilIsPrimeTest(val param: Param) {
    data class Param(val value: Long, val status: Boolean)

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun supplyData(): Collection<Param> = BufferedReader(FileReader(PATH, StandardCharsets.UTF_8)).lines()
                .map { val info = it.split(" "); Param(info[0].toLong(), info[1].toBoolean()) }
                .toList()
    }

    @Test
    fun givenValue_whenLongNUmber_returnsPrimeStatus() {
        assertEquals(param.status, param.value.isPrime())
    }
}