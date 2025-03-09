package org.csystem.app.android.payment.repository.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.csystem.app.android.payment.repository.dao.ICategoryDao
import org.csystem.app.android.payment.repository.db.PaymentLocalDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryDaoModule {
    @Provides
    @Singleton
    fun provideCategoryDao(db: PaymentLocalDatabase): ICategoryDao = db.categoryDao()
}