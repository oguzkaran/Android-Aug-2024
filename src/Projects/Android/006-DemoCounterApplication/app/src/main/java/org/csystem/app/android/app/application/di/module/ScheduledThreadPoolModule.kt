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
object ScheduledThreadPoolModule {
    @Provides
    @Singleton
    @Named("scheduledExecutorService")
    fun provideScheduledExecutorService(): ScheduledExecutorService = Executors.newScheduledThreadPool(2);
}