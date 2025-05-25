package org.csystem.app.android.randomusers.api.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.csystem.app.android.randomusers.api.me.constant.RANDOM_USER_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RandomUserRetrofitModule {
    @Provides
    @Singleton
    @Named("random_user")
    fun provideRandomUserRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RANDOM_USER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}