/**
 * Complex.kt
 * Class that represents a complex number
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.math

import kotlin.math.absoluteValue
import kotlin.math.sqrt

data class Complex(val real: Double = 0.0, val imag: Double = 0.0) {
    val norm: Double
        get() = sqrt(real * real + imag * imag)
    val length: Double
        get() = norm
    val conjugate: Complex
        get() = Complex(real, -imag)

    operator fun component3() = norm
    operator fun component4() = conjugate

    override fun equals(other: Any?) =
        other is Complex && (real - other.real).absoluteValue < 0.00001 && (imag - other.imag).absoluteValue < 0.00001

    override fun toString() = "($real, $imag)"
}