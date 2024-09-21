/**
 * Complex.kt
 * Class that represents a complex number
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.math

import kotlin.math.sqrt

class Complex(val real: Double = 0.0, val imag: Double = 0.0) {
    val norm: Double  
        get() = sqrt(real * real + imag * imag)
    val length: Double  
        get() = norm  
    val conjugate: Complex
        get() = Complex(real, -imag)

    override fun toString() = "($real, $imag)"
}