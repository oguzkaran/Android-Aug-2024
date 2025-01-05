package org.csystem.app.android.payment.application.module.datetime

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import java.time.LocalDateTime

@Module
@InstallIn(ActivityComponent::class)
object LocalDateModule {
    @Provides
    fun provideLocalDate(@ApplicationContext context: Context): LocalDate {
        Log.i("datetime-module", "provideLocalDate")
        Toast.makeText(context, "provideLocalDate", Toast.LENGTH_SHORT).show()

        return LocalDate.now()
    }
}