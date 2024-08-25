package org.csystem.math

import kotlin.math.sqrt

class Complex(var real: Double = 0.0, var imag: Double = 0.0) {
    val norm: Double  
        get() = sqrt(real * real + imag * imag)
    val length: Double  
        get() = norm  
    val conjugate: Complex  
        get() = Complex(real, -imag)
}