/**
 * MutableComplex.kt
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.math

import kotlin.math.absoluteValue
import kotlin.math.sqrt

data class MutableComplex(var real: Double = 0.0, var imag: Double = 0.0) {
    val norm: Double
        get() = sqrt(real * real + imag * imag)
    val length: Double
        get() = norm
    val conjugate: MutableComplex
        get() = MutableComplex(real, -imag)

    override fun equals(other: Any?) =
        other is MutableComplex && (real - other.real).absoluteValue < 0.00001 && (imag - other.imag).absoluteValue < 0.00001

    override fun toString() = "($real, $imag)"
}