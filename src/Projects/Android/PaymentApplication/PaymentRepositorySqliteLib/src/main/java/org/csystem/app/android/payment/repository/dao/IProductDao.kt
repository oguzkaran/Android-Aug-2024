package org.csystem.app.android.payment.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import org.csystem.app.android.payment.repository.entity.Product

@Dao
interface IProductDao {
    @Upsert
    fun save(product: Product)

    @Upsert
    fun save(vararg products: Product)

    @Query("SELECT * FROM products")
    fun findAll(): List<Product>

    @Query("SELECT * FROM products WHERE code = :code")
    fun findByCode(code: String): Product

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM products")
    fun deleteAll()
}
