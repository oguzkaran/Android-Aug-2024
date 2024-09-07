package org.csystem.kotlin.util.numeric

fun digits(a: Long): IntArray {
    TODO()
}

fun digits(a: Int) = digits(a.toLong())

fun digits(a: Short) = digits(a.toLong())

fun digits(a: Byte) = digits(a.toLong())