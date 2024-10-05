/**
 * Vector3.kt
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.math.geometry

data class Vector2F(val x: Float, val y: Float) {
    operator fun times(value: Float) = Vector2F(x * value, y * value)
    //...
}

infix fun Float.to(y: Float) = Vector2F(this, y)