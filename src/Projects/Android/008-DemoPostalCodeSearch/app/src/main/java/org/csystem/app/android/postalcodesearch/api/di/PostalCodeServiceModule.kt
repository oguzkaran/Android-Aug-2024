package org.csystem.app.android.postalcodesearch.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.csystem.app.android.postalcodesearch.api.geonames.service.IPostalCodeService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostalCodeServiceModule {
    @Provides
    @Singleton
    fun providePostalCodeService(retrofit: Retrofit): IPostalCodeService = retrofit.create(IPostalCodeService::class.java)
}