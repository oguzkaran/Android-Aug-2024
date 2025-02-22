package org.csystem.kotlin.util

import org.csystem.kotlin.util.numeric.numToTextTR
import org.junit.Test
import kotlin.test.Ignore
import kotlin.test.assertEquals

class NumberUtilNumToStrTRTest {
    @Test
    @Ignore("Not yet implemented")
    fun givenLongValue_whenNumber_returnsTurkishText() {
        val value = 1_234_567_890L;
        val expected = "bir milyar iki yüz otuz dört milyon beş yüz altmış yedi bin sekiz yüz doksan"

        assertEquals(expected, value.numToTextTR())
    }
}