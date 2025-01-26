package org.csystem.app.android.app.application.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduledThreadPool {
    @Provides
    @Singleton
    @Named("mainActivityScheduledExecutorService")
    fun provideScheduledExecutorService(): ScheduledExecutorService {
        return Executors.newScheduledThreadPool(1);
    }
}