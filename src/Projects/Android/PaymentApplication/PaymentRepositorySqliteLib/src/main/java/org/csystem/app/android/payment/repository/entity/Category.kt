package org.csystem.app.android.payment.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("categories")
data class Category(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "category_id") val id: Long = 0,
                    val description: String)
