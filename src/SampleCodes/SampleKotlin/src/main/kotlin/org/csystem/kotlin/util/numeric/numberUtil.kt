package org.csystem.kotlin.util.numeric

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.log10

private val ones = arrayOf("", "bir", "iki", "üç", "dört", "beş", "altı", "yedi", "sekiz", "dokuz")
private val tens = arrayOf("", "on", "yirmi", "otuz", "kırk", "elli", "altmış", "yetmiş", "seksen", "doksan")

private fun digits(a: Long, n: Int): IntArray {
    val count = (log10(abs(a).toDouble()) / n).toInt() + 1
    val d = IntArray(count)
    val divider = 10.0.pow(n).toInt()
    var temp = abs(a)

    for (i in count - 1 downTo 0) {
        d[i] = (temp % divider).toInt()
        temp /= divider
    }

    return d
}

private fun numToStr3DigitsTR(value: Int): String {
    if (value == 0)
        return "sıfır"

    val sb = if (value > 0) StringBuilder() else java.lang.StringBuilder("eksi")
    val temp = abs(value)
    val a = temp / 100
    val b = temp / 10 % 10
    val c = temp % 10

    if (a != 0) {
        if (a != 1)
            sb.append(ones[a])

        sb.append("yüz")
    }

    if (b != 0)
        sb.append(tens[b])

    if (c != 0)
        sb.append(ones[c])

    return sb.toString()
}

fun countDigits(a: Long) = if (a != 0L) log10(abs(a).toDouble()).toInt() + 1 else 1

fun digits(a: Long) = digits(a, 1)

fun digitsInTwos(a: Long) = digits(a, 2)

fun digitsInThrees(a: Long) = digits(a, 3)