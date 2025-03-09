package org.csystem.app.android.payment.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("products", foreignKeys = [ForeignKey(
    entity = Category::class,
    parentColumns = ["category_id"],
    childColumns = ["category_id"],
    onDelete = ForeignKey.CASCADE
)])
data class Product(@PrimaryKey val code: String = "",
                   @ColumnInfo(name = "category_id") val categoryId: Long = 0,
                   val name: String = "",
                   @ColumnInfo(name = "unit_price") val unitPrice: Double = 0.0)