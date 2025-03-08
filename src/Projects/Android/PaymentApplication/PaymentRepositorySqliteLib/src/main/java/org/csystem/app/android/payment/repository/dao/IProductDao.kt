package org.csystem.app.android.payment.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.csystem.app.android.payment.repository.entity.Product

@Dao
interface IProductDao {
    @Insert
    fun insert(product: Product)

    @Insert
    fun insert(vararg products: Product)

    @Query("SELECT * FROM products")
    fun findAll(): List<Product>

    @Query("SELECT * FROM products WHERE code = :code")
    fun findByCode(code: String): Product

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM products")
    fun deleteAll()
}