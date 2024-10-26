package org.csystem.app

import org.csystem.kotlin.util.console.readInt

fun main() {
    println(generateSequence { readInt("Bir sayı giriniz:") }
        .takeWhile { it != 0 }
        .sum())
}