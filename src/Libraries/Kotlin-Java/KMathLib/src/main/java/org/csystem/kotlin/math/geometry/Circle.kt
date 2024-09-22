/**
 * Circle.kt
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.math.geometry

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.absoluteValue

open class Circle(radius: Double = 0.0) {
    var radius: Double = abs(radius)
        set(value) {
            field = abs(value)
        }

    val area: Double
        get() = PI * radius * radius

    val circumference: Double
        get() = 2 * PI * radius

    operator fun component1() = radius
    operator fun component2() = area
    operator fun component3() = circumference

    override fun equals(other: Any?) = other is Circle && (radius - other.radius).absoluteValue < 0.00001
    override fun toString() = "Radius:$radius, Area:$area, Circumference:$circumference"
}
