package org.csystem.app

import org.csystem.kotlin.math.random.nextMutableComplex

import org.csystem.kotlin.math.MutableComplex
import kotlin.random.Random

operator fun MutableComplex.invoke() = println(this)

fun main() {
    val z = Random.nextMutableComplex(-10, 10)

    z()
    println(z(2.3, 4.5))
    z()
    println(z(8.9))
    z()
}