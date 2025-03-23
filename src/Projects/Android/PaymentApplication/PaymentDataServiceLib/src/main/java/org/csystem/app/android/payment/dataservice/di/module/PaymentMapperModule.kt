package org.csystem.app.android.payment.dataservice.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.csystem.app.android.payment.dataservice.mapper.IPaymentMapper
import org.mapstruct.factory.Mappers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PaymentMapperModule {
    @Provides
    @Singleton
    fun providePaymentMapper() : IPaymentMapper = Mappers.getMapper(IPaymentMapper::class.java)
}