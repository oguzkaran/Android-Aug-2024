package org.csystem.app.android.payment.repository.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.csystem.android.library.util.datetime.converter.DateTimeToMillisecondsConverter
import org.csystem.android.library.util.datetime.converter.LocalDateTimeToLocalDate
import org.csystem.app.android.payment.repository.db.PaymentLocalDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class PaymentLocalDatabaseModule {
    @Provides
    @Singleton
    fun providePaymentLocalDatabase(@ApplicationContext  context: Context,
                                    dateTimeToMillisecondsConverter: DateTimeToMillisecondsConverter,
                                    localDateTimeToLocalDate: LocalDateTimeToLocalDate)
    : PaymentLocalDatabase = Room.databaseBuilder(context, PaymentLocalDatabase::class.java, "paymentdb")
        .addTypeConverter(dateTimeToMillisecondsConverter).addTypeConverter(localDateTimeToLocalDate).build()
}