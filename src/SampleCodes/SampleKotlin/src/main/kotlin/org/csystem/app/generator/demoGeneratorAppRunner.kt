package org.csystem.app.generator

import org.csystem.kotlin.math.Complex
import org.csystem.kotlin.math.geometry.Point
import org.csystem.kotlin.util.console.readInt

fun runDemoGeneratorApp() {
    val count = readInt("Input count:")

    val array = createArray(count)

    for (a in array) {
        println("------------------------------------------------------")
        println(a.javaClass.name)

        when (a) {
            is Point -> println("(${a.x}, ${a.y}")
            is Complex -> println("|(${a.real}, ${a.real})| = ${a.norm}")
            is Int -> println("$a * $a = ${a * a}")
            is Boolean -> println("Not of:${!a}")
            is Char -> println("Character:$a, Upper:${a.uppercase()}")
        }

        println("------------------------------------------------------")
    }
}