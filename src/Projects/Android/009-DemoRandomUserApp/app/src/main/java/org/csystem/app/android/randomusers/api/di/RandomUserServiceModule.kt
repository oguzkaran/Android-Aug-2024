package org.csystem.app.android.randomusers.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.csystem.app.android.randomusers.api.me.service.IRandomUserService
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RandomUserServiceModule {
    @Provides
    @Singleton
    fun provideRandomUserService(@Named("random_user") retrofit: Retrofit): IRandomUserService = retrofit.create(IRandomUserService::class.java)
}