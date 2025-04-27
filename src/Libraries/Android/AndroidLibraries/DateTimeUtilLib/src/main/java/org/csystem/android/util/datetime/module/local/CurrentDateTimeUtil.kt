package org.csystem.android.util.datetime.module.local

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.android.util.datetime.annotation.CurrentLocalDateInterceptor
import org.csystem.android.util.datetime.annotation.CurrentLocalDateTimeInterceptor
import org.csystem.android.util.datetime.annotation.CurrentLocalTimeInterceptor
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * A Dagger module that provides the current date, time, and datetime instances.
 * This module is installed in the `ActivityComponent` scope, meaning
 * the provided dependencies will live as long as the activity.
 */
@Module
@InstallIn(ActivityComponent::class)
object CurrentDateTimeUtil {

    /**
     * Provides the current `LocalDate` instance.
     *
     * @return The current `LocalDate` obtained using `LocalDate.now()`.
     * @annotation CurrentLocalDateInterceptor A custom annotation used to qualify this provider.
     */
    @Provides
    @CurrentLocalDateInterceptor
    fun provideLocalDate(): LocalDate = LocalDate.now()

    /**
     * Provides the current `LocalDateTime` instance.
     *
     * @return The current `LocalDateTime` obtained using `LocalDateTime.now()`.
     * @annotation CurrentLocalDateTimeInterceptor A custom annotation used to qualify this provider.
     */
    @Provides
    @CurrentLocalDateTimeInterceptor
    fun provideLocalDateTime(): LocalDateTime = LocalDateTime.now()

    /**
     * Provides the current `LocalTime` instance.
     *
     * @return The current `LocalTime` obtained using `LocalTime.now()`.
     * @annotation CurrentLocalTimeInterceptor A custom annotation used to qualify this provider.
     */
    @Provides
    @CurrentLocalTimeInterceptor
    fun provideLocalTime(): LocalTime = LocalTime.now()
}