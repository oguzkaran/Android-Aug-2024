package org.csystem.app.android.payment.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.csystem.app.android.payment.repository.dao.ICategoryDao
import org.csystem.app.android.payment.repository.dao.IPaymentDao
import org.csystem.app.android.payment.repository.dao.IPaymentToProductDao
import org.csystem.app.android.payment.repository.dao.IProductDao
import org.csystem.app.android.payment.repository.entity.Category
import org.csystem.app.android.payment.repository.entity.Payment
import org.csystem.app.android.payment.repository.entity.PaymentToProduct
import org.csystem.app.android.payment.repository.entity.Product

@Database(entities = [Product::class, Category::class, Payment::class, PaymentToProduct::class], version = 1)
abstract class PaymentLocalDatabase : RoomDatabase() {
    abstract fun productDao(): IProductDao
    abstract fun categoryDao(): ICategoryDao
    abstract fun paymentDao(): IPaymentDao
    abstract fun paymentToProductDao(): IPaymentToProductDao
}