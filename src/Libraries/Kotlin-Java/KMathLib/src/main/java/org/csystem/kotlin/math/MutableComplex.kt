/**
 * MutableComplex.kt
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.math

import kotlin.math.absoluteValue
import kotlin.math.sqrt

private fun add(re1: Double = 0.0, im1: Double = 0.0, re2: Double = 0.0, im2: Double = 0.0) = MutableComplex(re1 + re2, im1 + im2)
private fun subtract(re1: Double = 0.0, im1: Double = 0.0, re2: Double = 0.0, im2: Double = 0.0) = add(re1, im1, -re2, -im2)

operator fun Double.plus(z: MutableComplex) = add(re1 = this, re2 = z.real, im2 = z.imag)
operator fun Double.minus(z: MutableComplex) = subtract(re1 = this, re2 = z.real, im2 = z.imag)

data class MutableComplex(var real: Double = 0.0, var imag: Double = 0.0) {
    val norm: Double
        get() = sqrt(real * real + imag * imag)
    val length: Double
        get() = norm
    val conjugate: MutableComplex
        get() = MutableComplex(real, -imag)

    operator fun plus(other: MutableComplex) = add(real, imag, other.real, other.imag)
    operator fun plus(value: Double) = add(real, imag, value)
    operator fun minus(other: MutableComplex) = subtract(real, imag, other.real, other.imag)
    operator fun minus(value: Double) = subtract(real, imag, value)
    operator fun times(other: MutableComplex): MutableComplex = TODO()
    operator fun times(value: Double): MutableComplex = TODO()
    operator fun div(other: MutableComplex): MutableComplex = TODO()
    operator fun div(value: Double): MutableComplex = TODO()

    operator fun unaryMinus() = MutableComplex(-real, -imag)
    operator fun unaryPlus() = this

    operator fun inc() = MutableComplex(real + 1, imag)
    operator fun dec() = MutableComplex(real - 1, imag)

    operator fun invoke(real: Double, imag: Double = this.imag): MutableComplex {
        this.real = real
        this.imag = imag

        return this
    }

    override operator fun equals(other: Any?) =
        other is MutableComplex && (real - other.real).absoluteValue < 0.00001 && (imag - other.imag).absoluteValue < 0.00001

    override fun toString() = "($real, $imag)"
}