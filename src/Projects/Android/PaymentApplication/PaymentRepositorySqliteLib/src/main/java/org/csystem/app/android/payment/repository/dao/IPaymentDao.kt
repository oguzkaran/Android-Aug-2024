package org.csystem.app.android.payment.repository.dao

import androidx.room.Dao
import androidx.room.Upsert
import org.csystem.app.android.payment.repository.entity.Payment

@Dao
interface IPaymentDao {
    @Upsert
    fun save(payment: Payment)

    @Upsert
    fun save(vararg payments: Payment)

    //...
}