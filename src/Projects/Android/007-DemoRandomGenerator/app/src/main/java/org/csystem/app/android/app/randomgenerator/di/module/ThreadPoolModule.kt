package org.csystem.app.android.app.randomgenerator.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ThreadPoolModule {
    @Provides
    @Singleton
    fun provideThreadPool(): ExecutorService = Executors.newSingleThreadExecutor()
}