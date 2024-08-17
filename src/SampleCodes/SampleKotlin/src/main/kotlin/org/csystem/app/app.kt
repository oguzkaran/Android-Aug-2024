package org.csystem.app

fun main() = runCountDigitsTest()

fun runCountDigitsTest() {
    while (true) {
        print("Bir sayı giriniz:")
        val value = readln().toInt()

        println("$value sayısının basamak sayısı:${countDigits(value)}")

        if (value == 0)
            break
    }

    println("Tekrar yapıyor musunuz?")
}

fun countDigits(a: Int): Int {
    var count = 0
    var a = a

    do {
        ++count
        a /= 10
    } while (a != 0)

    return count
}