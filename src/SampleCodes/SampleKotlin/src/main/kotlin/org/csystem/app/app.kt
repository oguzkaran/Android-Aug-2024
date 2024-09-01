package org.csystem.app

import org.csystem.kotlin.util.array.randomIntArray
import org.csystem.kotlin.util.array.write
import org.csystem.kotlin.util.console.readInt

fun main() = runRandomIntArrayTest()

fun runRandomIntArrayTest() {
    while (true) {
        val count = readInt("Dizinin eleman sayısını giriniz:")

        if (count <= 0)
            break
        val a = randomIntArray(count, 0, 100)

        write(2, a)
    }

    println("Tekrar yapıyor musunuz?")
}