package org.csystem.kotlin.math.equation

import kotlin.math.sqrt

data class QuadraticRootInfo(val x1: Double, val x2: Double, val exists: Boolean)

fun findQuadraticEquationRoots(a: Double, b: Double, c: Double) : QuadraticRootInfo {
    fun calculateDelta() = b * b - 4 * a * c
    val delta = calculateDelta()

    if (delta >= 0) {
        val sqrtDelta = sqrt(delta)

        return QuadraticRootInfo((-b + sqrtDelta) / (2 * a), (-b - sqrtDelta) / (2 * a), true)
    }

    return QuadraticRootInfo(Double.NaN, Double.NaN, false)
}
