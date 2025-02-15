package org.csystem.android.library.util.datetime.module.annotation

import javax.inject.Qualifier

/**
 * A qualifier annotation to indicate the current local date-time interceptor.
 * This annotation is used to distinguish between different implementations
 * of the same type, specifically for intercepting the current local date-time.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CurrentLocalDateTimeInterceptor