/**
 * Quadruple.kt
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.util.tuple

import java.io.Serializable

data class Quadruple<out T1, out T2, out T3, out T4>(val first: T1, val second: T2, val third: T3, val forth: T4) :
    Serializable {
    fun toList() = listOf(first, second, third, forth)
    override fun toString() = "($first, $second, $third, $forth)"
}