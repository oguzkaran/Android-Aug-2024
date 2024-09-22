/**
 * Point.kt
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.math.geometry

import kotlin.math.absoluteValue
import kotlin.math.sqrt

data class Point(val x: Double = 0.0, val y: Double = 0.0) {
    fun distance(a: Double = 0.0, b: Double = 0.0) = sqrt((x - a) * (x - a) + (y - b) * (y - b))
    fun distance(other: Point) = distance(other.x, other.y)

    override fun equals(other: Any?) =
        other is Point && (x - other.x).absoluteValue < 0.00001 && (y - other.y).absoluteValue < 0.00001

    override fun toString() = "($x, $y)"
}