package org.csystem.android.library.util.datetime.module.formatter

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.csystem.android.library.util.datetime.annotation.DateTimeFormatterTRInterceptor
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateTimeFormatterTRModule {
    @Provides
    @Singleton
    @DateTimeFormatterTRInterceptor
    fun provideDateTineFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH.mm.ss")
}