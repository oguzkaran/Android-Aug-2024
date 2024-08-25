package org.csystem.math.geometry

import kotlin.math.sqrt

class Point(var x: Double = 0.0, var y: Double = 0.0) {
    fun distance(a: Double = 0.0, b: Double = 0.0) = sqrt((x - a) * (x - a) + (y - b) * (y - b))
    fun distance(other: Point) = distance(other.x, other.y)

    fun offset(dx: Double, dy: Double = dx) {
        x += dx
        y += dy
    }
}