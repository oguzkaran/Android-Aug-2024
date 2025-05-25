package org.csystem.app.android.randomusers.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.csystem.app.android.randomusers.api.me.constant.DUMMY_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataServiceRetrofitModule {
    @Provides
    @Singleton
    @Named("image_retrofit_service")
    fun provideImageRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DUMMY_URL) //Any url starts with "http" or "https" is OK. for that reason
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}