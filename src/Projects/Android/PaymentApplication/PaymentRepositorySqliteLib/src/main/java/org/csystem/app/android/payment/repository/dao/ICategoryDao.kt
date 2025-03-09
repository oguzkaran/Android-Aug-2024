package org.csystem.app.android.payment.repository.dao

import androidx.room.Dao
import androidx.room.Upsert
import org.csystem.app.android.payment.repository.entity.Category


@Dao
interface ICategoryDao {
    @Upsert
    fun save(category: Category)
    //...
}