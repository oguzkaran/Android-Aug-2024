package org.csystem.android.util.datetime.annotation

import javax.inject.Qualifier

/**
 * A qualifier annotation to indicate the date-time formatter for Turkish locale.
 * This annotation is used to distinguish between different implementations
 * of the same type, specifically for formatting date-time in Turkish locale.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DateTimeFormatterTRInterceptor