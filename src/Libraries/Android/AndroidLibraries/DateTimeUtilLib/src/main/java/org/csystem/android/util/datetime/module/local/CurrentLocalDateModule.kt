package org.csystem.android.util.datetime.module.local

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.android.util.datetime.annotation.CurrentLocalDateInterceptor
import java.time.LocalDate

@Module
@InstallIn(ActivityComponent::class)
object CurrentLocalDateModule {
    @Provides
    @CurrentLocalDateInterceptor
    fun provideLocalDate(): LocalDate = LocalDate.now()
}