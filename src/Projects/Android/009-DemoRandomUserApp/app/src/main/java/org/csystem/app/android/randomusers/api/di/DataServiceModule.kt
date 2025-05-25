package org.csystem.app.android.randomusers.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.csystem.app.android.randomusers.api.me.service.IDataService
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataServiceModule {
    @Provides
    @Singleton
    fun provideImageService(@Named("image_retrofit_service") retrofit: Retrofit): IDataService = retrofit.create(IDataService::class.java)
}