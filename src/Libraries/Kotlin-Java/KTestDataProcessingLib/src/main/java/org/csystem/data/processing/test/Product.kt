package org.csystem.data.processing.test

import java.math.BigDecimal

data class Product(
    var id: Int = 0,
    var name: String = "",
    var stock: Int = 0,
    var cost: BigDecimal = BigDecimal.ZERO,
    var price: BigDecimal = BigDecimal.ZERO
) : Comparable<Product> {

    override fun compareTo(other: Product) = id - other.id

    override fun hashCode(): Int = id

    override fun equals(other: Any?) = other is Product && other.id == id
}