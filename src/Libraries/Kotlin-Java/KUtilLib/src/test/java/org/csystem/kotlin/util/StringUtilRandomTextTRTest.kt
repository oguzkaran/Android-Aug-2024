package org.csystem.kotlin.util

import org.csystem.kotlin.util.string.randomTextTR
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.random.Random
import kotlin.test.Ignore
import kotlin.test.assertTrue

@RunWith(Parameterized::class)
class StringUtilRandomTextTRTest(val n: Int) {
    companion object {
        private const val LETTERS_TR = "abcçdefgğhıijklmnoöprsştuüvyz"

        @JvmStatic
        @Parameterized.Parameters
        fun supplyData(): Collection<Int> = (1..100).toList()

        private fun checkText(text: String): Boolean {
            TODO("Not yet implemented")
        }
    }

    @Test
    @Ignore("checkText not yet implemented")
    fun givenValue_whenCount_thenReturnsRandomTurkishText() {
        assertTrue ("Invalid random text"){ checkText(Random.randomTextTR(n)) }
    }
}