package org.csystem.app.android.payment.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("products")
data class Product(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "product_id") val id: Long = 0,
                   val code: String,
                   val name: String,
                   @ColumnInfo(name = "unit_price") val unitPrice: Double)