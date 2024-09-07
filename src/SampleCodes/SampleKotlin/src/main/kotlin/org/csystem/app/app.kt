package org.csystem.util.numeric.test

import org.csystem.kotlin.util.console.printArray
import org.csystem.kotlin.util.console.readInt
import org.csystem.kotlin.util.numeric.digits
import kotlin.random.Random

fun main() = runDigitsLongTest()

fun runDigitsLongTest() {
    val count = readInt("Bir sayı giriniz:")

    for (i in 1..count) {
        val value = Random.nextLong();
        print("$value -> ")
        printArray(digits(value))
    }

    println("Tekrar yapıyor musunuz?")
}