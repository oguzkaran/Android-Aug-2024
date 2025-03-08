package org.csystem.app.android.payment.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.csystem.app.android.payment.repository.dao.IProductDao
import org.csystem.app.android.payment.repository.entity.Product

@Database(entities = [Product::class], version = 1)
abstract class PaymentLocalDatabase : RoomDatabase() {
    abstract fun productDao(): IProductDao
}