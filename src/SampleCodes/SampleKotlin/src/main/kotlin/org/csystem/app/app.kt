package org.csystem.app

import org.csystem.kotlin.util.console.readInt

fun main() {
    val a = readInt("Birinci sayıyı giriniz:")
    val b = readInt("İkinci  sayıyı giriniz:")

    println(sum(10, a, b))
    println(sum(10))
    println(sum(10, a))
}

fun sum(init: Int, vararg values: Int): Int {
    var total = init

    for (v in values)
        total += v

    return total
}