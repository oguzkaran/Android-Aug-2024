package org.csystem.kotlin.util.numeric

import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow

private val onesTR = arrayOf("", "bir", "iki", "üç", "dört", "beş", "altı", "yedi", "sekiz", "dokuz")
private val tensTR = arrayOf("", "on", "yirmi", "otuz", "kırk", "elli", "altmış", "yetmiş", "seksen", "doksan")

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

fun Int.countDigits() = toLong().countDigits()
fun Long.countDigits() = if (this != 0L) log10(abs(this).toDouble()).toInt() + 1 else 1

fun Long.digits() = digits(1)

fun Long.digitsInTwos() = digits(2)

fun Long.digitsInThrees() = digits(3)

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

fun Int.isPrime() = toLong().isPrime()

fun Long.numToTextTR(): String {
    TODO()
}