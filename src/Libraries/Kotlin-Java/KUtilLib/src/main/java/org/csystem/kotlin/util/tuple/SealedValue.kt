package org.csystem.kotlin.util.tuple

sealed class SealedValue<out T>(val value: T) {
    override fun equals(other: Any?) = other is SealedValue<*> && value == other.value

    override fun hashCode() = value.hashCode()

    override fun toString() = value.toString()
}