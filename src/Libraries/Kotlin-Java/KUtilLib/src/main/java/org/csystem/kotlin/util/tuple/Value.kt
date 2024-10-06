/**
 * Value.kt
 * @author Android-Aug-2024 group
 */
package org.csystem.kotlin.util.tuple

import java.io.Serializable

data class Value<out T>(val value: T) : Serializable {
    fun toList(): List<T> = listOf(value)
}