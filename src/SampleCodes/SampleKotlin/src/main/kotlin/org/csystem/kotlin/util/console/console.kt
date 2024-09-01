package org.csystem.kotlin.util.console

fun readInt(prompt: String): Int {
    print(prompt)
    return readln().toInt()
}

fun readLong(prompt: String): Long {
    print(prompt)
    return readln().toLong()
}

fun readDouble(prompt: String): Double {
    print(prompt)
    return readln().toDouble()
}