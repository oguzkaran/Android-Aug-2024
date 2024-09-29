package org.csystem.kotlin.math.random

import org.csystem.kotlin.math.Complex
import org.csystem.kotlin.math.MutableComplex
import kotlin.random.Random

fun Random.nextMutableComplex(origin: Double, bound: Double) =
    MutableComplex(this.nextDouble(origin, bound), this.nextDouble(origin, bound))

fun Random.nextMutableComplex(origin: Int, bound: Int) = this.nextMutableComplex(origin.toDouble(), bound.toDouble())

fun Random.nextMutableComplex(origin: Long, bound: Long) = this.nextMutableComplex(origin.toDouble(), bound.toDouble())

fun Random.nextComplex(origin: Double, bound: Double) =
    Complex(this.nextDouble(origin, bound), this.nextDouble(origin, bound))

fun Random.nextComplex(origin: Int, bound: Int) = this.nextComplex(origin.toDouble(), bound.toDouble())

fun Random.nextComplex(origin: Long, bound: Long) = this.nextComplex(origin.toDouble(), bound.toDouble())