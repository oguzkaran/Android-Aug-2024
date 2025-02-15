package org.csystem.android.library.util.datetime.module.annotation

import javax.inject.Qualifier

/**
 * A qualifier annotation to indicate the date formatter for Turkish locale.
 * This annotation is used to distinguish between different implementations
 * of the same type, specifically for formatting dates in Turkish locale.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DateFormatterTRInterceptor