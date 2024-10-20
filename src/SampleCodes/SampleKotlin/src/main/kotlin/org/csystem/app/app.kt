package org.csystem.app

import org.csystem.kotlin.util.generator.random.RandomIntGenerator

fun main() {
    val rig = RandomIntGenerator(10, 0, 100)

    for (value in rig)
        print("%02d ".format(value))

    println("\n---------------------------------------------------------------------------")

    for (value in rig)
        print("%02d ".format(value))
}