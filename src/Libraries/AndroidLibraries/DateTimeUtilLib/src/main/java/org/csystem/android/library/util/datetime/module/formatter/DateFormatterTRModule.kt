package org.csystem.android.library.util.datetime.module.formatter

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.csystem.android.library.util.datetime.module.annotation.DateFormatterTRInterceptor
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateFormatterTRModule {
    @Provides
    @Singleton
    @DateFormatterTRInterceptor
    fun provideDateTineFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
}