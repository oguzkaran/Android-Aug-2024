package org.csystem.app

import org.csystem.kotlin.util.console.readDouble

fun main() {
    var a = 0.0;
    var b: Double

    while (true) {
        try {
            a = readDouble("Input the first number:", "Invalid Value!...")
            b = readDouble("Input the second number:", "Invalid value!...")

            println(divide(a, b))
        } catch (ex: IllegalArgumentException) {
            println("Reason:${ex.message}")
        }
        if (a == 0.0)
            break
    }
}

fun divide(a: Double, b: Double): Double {
    if (b == 0.0) {
        val msg = when (a) {
            0.0 -> "Undefined"
            else -> "Indeterminate"
        }

        throw IllegalArgumentException(msg)
    }

    return a / b
}