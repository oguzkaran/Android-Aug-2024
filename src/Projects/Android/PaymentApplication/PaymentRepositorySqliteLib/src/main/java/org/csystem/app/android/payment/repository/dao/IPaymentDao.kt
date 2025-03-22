package org.csystem.app.android.payment.repository.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.csystem.app.android.payment.repository.entity.Payment
import org.csystem.app.android.payment.repository.entity.PaymentToProduct
import java.time.LocalDate
import java.time.LocalDateTime

@Dao
interface IPaymentDao {
    @Upsert
    fun save(payment: Payment): Long

    @Upsert
    fun save(vararg payments: Payment)

    @Query("SELECT ptp.product_code, p.payment_id, ptp.unit_price, ptp.amount FROM payments p INNER JOIN payments_to_products ptp ON p.payment_id = ptp.payment_id WHERE date(p.date_time / 1000, 'unixepoch', 'localtime') = date(:date / 1000, 'unixepoch', 'localtime')")
    fun findByDate(date: LocalDate): List<PaymentToProduct>
    //...
}