/**
 * Vector3.kt
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.math.geometry

data class Vector3F(val x: Float, val y: Float, val z: Float) {
    operator fun times(value: Float) = Vector3F(x * value, y * value, z * value)
    //...
}