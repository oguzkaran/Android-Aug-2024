package org.csystem.android.library.util.datetime.annotation

import javax.inject.Qualifier

/**
 * A qualifier annotation to indicate the current local time interceptor.
 * This annotation is used to distinguish between different implementations
 * of the same type, specifically for intercepting the current local time.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CurrentLocalTimeInterceptor