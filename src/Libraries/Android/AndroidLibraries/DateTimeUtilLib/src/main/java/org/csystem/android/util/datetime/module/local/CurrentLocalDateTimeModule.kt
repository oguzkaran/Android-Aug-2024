package org.csystem.android.util.datetime.module.local

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.android.util.datetime.annotation.CurrentLocalDateTimeInterceptor
import java.time.LocalDateTime

@Module
@InstallIn(ActivityComponent::class)
object CurrentLocalDateTimeModule {
    @Provides
    @CurrentLocalDateTimeInterceptor
    fun provideLocalDateTime(): LocalDateTime = LocalDateTime.now()
}