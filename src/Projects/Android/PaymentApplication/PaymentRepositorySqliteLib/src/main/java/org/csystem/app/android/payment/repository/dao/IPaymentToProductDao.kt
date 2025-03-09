package org.csystem.app.android.payment.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import org.csystem.app.android.payment.repository.entity.PaymentToProduct

@Dao
interface IPaymentToProductDao {
    @Insert
    fun save(paymentToProduct: PaymentToProduct)

    //...
}