package org.csystem.app.android.payment.application.module.datetime

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.csystem.app.android.payment.application.module.datetime.annotation.DateTimeFormatterInterceptor
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateTimeFormatterModule {
    @Provides
    @Singleton
    @DateTimeFormatterInterceptor
    fun provideDateTimeFormatter(@ApplicationContext context: Context): DateTimeFormatter {
        Log.i("formatter-module", "provideDateTimeFormatter")
        Toast.makeText(context, "provideDateTimeFormatter", Toast.LENGTH_SHORT).show()

        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    }
}