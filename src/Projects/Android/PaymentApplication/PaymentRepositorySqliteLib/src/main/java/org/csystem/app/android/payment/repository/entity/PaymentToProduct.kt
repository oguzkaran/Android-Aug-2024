package org.csystem.app.android.payment.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import java.time.LocalDateTime

@Entity(tableName = "payments_to_products",
    primaryKeys = ["product_code", "payment_id"],
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["code"],
        childColumns = ["product_code"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Payment::class,
        parentColumns = ["payment_id"],
        childColumns = ["payment_id"],
        onDelete = ForeignKey.CASCADE
    )])
data class PaymentToProduct (@ColumnInfo(name = "product_code") var productCode: String = "",
                             @ColumnInfo(name = "payment_id") var paymentId: Long = 0,
                             @ColumnInfo(name = "unit_price") var unitPrice: Double = 0.0,
                             var amount: Double = 0.0)