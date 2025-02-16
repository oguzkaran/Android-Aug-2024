package org.csystem.kotlin.util.numeric

        import kotlin.math.abs
        import kotlin.math.log10
        import kotlin.math.pow

        private val onesTR = arrayOf("", "bir", "iki", "üç", "dört", "beş", "altı", "yedi", "sekiz", "dokuz")
        private val tensTR = arrayOf("", "on", "yirmi", "otuz", "kırk", "elli", "altmış", "yetmiş", "seksen", "doksan")

        /**
         * Extension function to get the digits of a Long number in groups of n digits.
         *
         * @param n The number of digits in each group.
         * @return An array of integers representing the digits in groups of n.
         */
        private fun Long.digits(n: Int): IntArray {
            val count = (log10(abs(this).toDouble()) / n).toInt() + 1
            val d = IntArray(count)
            val divider = 10.0.pow(n).toInt()
            var temp = abs(this)

            for (i in count - 1 downTo 0) {
                d[i] = (temp % divider).toInt()
                temp /= divider
            }

            return d
        }

        /**
         * Extension function to convert a 3-digit integer to its Turkish text representation.
         *
         * @return A string representing the 3-digit integer in Turkish.
         */
        private fun Int.numToText3DigitsTR(): String {
            if (this == 0)
                return "sıfır"

            val sb = if (this > 0) StringBuilder() else java.lang.StringBuilder("eksi")
            val temp = abs(this)
            val a = temp / 100
            val b = temp / 10 % 10
            val c = temp % 10

            if (a != 0) {
                if (a != 1)
                    sb.append(onesTR[a])

                sb.append("yüz")
            }

            if (b != 0)
                sb.append(tensTR[b])

            if (c != 0)
                sb.append(onesTR[c])

            return sb.toString()
        }

        /**
         * Extension function to count the number of digits in an integer.
         *
         * @return The number of digits in the integer.
         */
        fun Int.countDigits() = toLong().countDigits()

        /**
         * Extension function to count the number of digits in a Long number.
         *
         * @return The number of digits in the Long number.
         */
        fun Long.countDigits() = if (this != 0L) log10(abs(this).toDouble()).toInt() + 1 else 1

        /**
         * Extension function to get the digits of a Long number.
         *
         * @return An array of integers representing the digits.
         */
        fun Long.digits() = digits(1)

        /**
         * Extension function to get the digits of a Long number in groups of two.
         *
         * @return An array of integers representing the digits in groups of two.
         */
        fun Long.digitsInTwos() = digits(2)

        /**
         * Extension function to get the digits of a Long number in groups of three.
         *
         * @return An array of integers representing the digits in groups of three.
         */
        fun Long.digitsInThrees() = digits(3)

        /**
         * Extension function to check if a Long number is prime.
         *
         * @return True if the Long number is prime, false otherwise.
         */
        fun Long.isPrime(): Boolean {
            if (this <= 1)
                return false

            if (this % 2 == 0L)
                return this == 2L

            if (this % 3 == 0L)
                return this == 3L

            if (this % 5 == 0L)
                return this == 5L

            if (this % 7 == 0L)
                return this == 7L

            var i = 11

            while (i * i <= this) {
                if (this % i == 0L)
                    return false

                i += 2
            }

            return true
        }

        /**
         * Extension function to check if an integer is prime.
         *
         * @return True if the integer is prime, false otherwise.
         */
        fun Int.isPrime() = toLong().isPrime()

        /**
         * Extension function to convert a Long number to its Turkish text representation.
         *
         * @return A string representing the Long number in Turkish.
         */
        fun Long.numToTextTR(): String {
            TODO()
        }