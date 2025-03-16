package org.csystem.app.android.payment.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity("payments")
data class Payment(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "payment_id") var id: Long = 0,
                   @ColumnInfo("date_time") var dateTime: LocalDateTime = LocalDateTime.now())
