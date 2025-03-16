package org.csystem.android.library.util.datetime.annotation

import javax.inject.Qualifier

/**
 * A qualifier annotation to indicate the time formatter for Turkish locale.
 * This annotation is used to distinguish between different implementations
 * of the same type, specifically for formatting time in Turkish locale.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TimeFormatterTRInterceptor