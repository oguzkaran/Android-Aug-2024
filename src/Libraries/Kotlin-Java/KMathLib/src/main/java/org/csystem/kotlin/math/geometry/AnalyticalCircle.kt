/**
 * AnalyticalCircle.kt
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.math.geometry

import kotlin.math.abs

class AnalyticalCircle(radius: Double = 0.0, x: Double = 0.0, y: Double = 0.0) : Circle(radius) {
    private val mCenter: MutablePoint = MutablePoint(x, y)

    var x: Double
        get() = mCenter.x
        set(value) {
            mCenter.x = value
        }

    var y: Double
        get() = mCenter.y
        set(value) {
            mCenter.y = value
        }

    fun setCenter(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    operator fun component4() = mCenter.x
    operator fun component5() = mCenter.y

    fun centerDistance(other: AnalyticalCircle) = mCenter.distance(other.mCenter)
    fun isTangent(other: AnalyticalCircle) = abs(centerDistance(other) - radius - other.radius) < 0.00001
    fun offset(dx: Double, dy: Double = dx) = mCenter.offset(dx, dy)

    override fun equals(other: Any?) = other is AnalyticalCircle && super.equals(other) && mCenter == other.mCenter
    override fun toString() = "${super.toString()}, Center:$mCenter"
}
