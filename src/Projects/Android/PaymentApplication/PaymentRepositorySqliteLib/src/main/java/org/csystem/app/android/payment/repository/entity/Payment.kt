package org.csystem.app.android.payment.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("payments")
data class Payment(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "payment_id") var id: Long = 0,
                   var total: Double = 0.0)
