package org.csystem.android.util.datetime.module.local

package org.csystem.android.util.datetime.module.local

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.csystem.android.util.datetime.annotation.CurrentLocalDateTimeInterceptor
import java.time.LocalDateTime

/**
 * A Dagger module that provides the current `LocalDateTime` instance.
 * This module is installed in the `ActivityComponent` scope, meaning
 * the provided dependencies will live as long as the activity.
 */
@Module
@InstallIn(ActivityComponent::class)
object CurrentLocalDateTimeModule {

    /**
     * Provides the current `LocalDateTime` instance.
     *
     * @return The current `LocalDateTime` obtained using `LocalDateTime.now()`.
     * @annotation CurrentLocalDateTimeInterceptor A custom annotation used to qualify this provider.
     */
    @Provides
    @CurrentLocalDateTimeInterceptor
    fun provideLocalDateTime(): LocalDateTime = LocalDateTime.now()
}