package org.csystem.android.library.util.datetime.module.local

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.android.library.util.datetime.annotation.CurrentLocalTimeInterceptor
import java.time.LocalTime

@Module
@InstallIn(ActivityComponent::class)
object CurrentLocalTimeModule {
    @Provides
    @CurrentLocalTimeInterceptor
    fun provideLocalDateTime(): LocalTime = LocalTime.now()
}