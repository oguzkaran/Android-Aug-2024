/**
 * Complex.kt
 * Class that represents a complex number
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.math

import kotlin.math.absoluteValue
import kotlin.math.sqrt

private fun add(re1: Double = 0.0, im1: Double = 0.0, re2: Double = 0.0, im2: Double = 0.0) =
    Complex(re1 + re2, im1 + im2)

private fun subtract(re1: Double = 0.0, im1: Double = 0.0, re2: Double = 0.0, im2: Double = 0.0) =
    add(re1, im1, -re2, -im2)

operator fun Double.plus(z: Complex) = add(re1 = this, re2 = z.real, im2 = z.imag)
operator fun Double.minus(z: Complex) = subtract(re1 = this, re2 = z.real, im2 = z.imag)

data class Complex(val real: Double = 0.0, val imag: Double = 0.0) {
    val norm: Double
        get() = sqrt(real * real + imag * imag)
    val length: Double
        get() = norm
    val conjugate: Complex
        get() = Complex(real, -imag)

    operator fun component3() = norm
    operator fun component4() = conjugate

    operator fun plus(other: Complex) = add(real, imag, other.real, other.imag)
    operator fun plus(value: Double) = add(real, imag, value)
    operator fun minus(other: Complex) = subtract(real, imag, other.real, other.imag)
    operator fun minus(value: Double) = subtract(real, imag, value)
    operator fun times(other: Complex): Complex = TODO()
    operator fun times(value: Double): Complex = TODO()
    operator fun div(other: Complex): Complex = TODO()
    operator fun div(value: Double): Complex = TODO()

    operator fun unaryMinus() = Complex(-real, -imag)
    operator fun unaryPlus() = this


    operator fun get(i: Int): Double {
        if (i < 0 || i > 1)
            throw IndexOutOfBoundsException("Index must 0(zero) or 1(one)")

        return if (i == 0) real else imag
    }

    override fun equals(other: Any?) =
        other is Complex && (real - other.real).absoluteValue < 0.00001 && (imag - other.imag).absoluteValue < 0.00001

    override fun toString() = "($real, $imag)"
}