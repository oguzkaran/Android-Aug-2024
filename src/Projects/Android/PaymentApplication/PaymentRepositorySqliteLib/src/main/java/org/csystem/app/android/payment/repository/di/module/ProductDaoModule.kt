package org.csystem.app.android.payment.repository.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.csystem.app.android.payment.repository.dao.IProductDao
import org.csystem.app.android.payment.repository.db.PaymentLocalDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductDaoModule {
    @Provides
    @Singleton
    fun provideProductDao(db: PaymentLocalDatabase): IProductDao = db.productDao()
}