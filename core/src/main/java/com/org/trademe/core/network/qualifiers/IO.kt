package com.org.trademe.core.network.qualifiers

import javax.inject.Qualifier

/**
 * Qualifier annotation for providing and injecting an IO-specific CoroutineContext.
 *
 * This qualifier allows Hilt/Dagger to distinguish between multiple CoroutineContext
 * implementations during dependency injection. When a class requires a background
 * dispatcher for I/O operations (network calls, database access, file operations),
 * this qualifier should be used.
 **/

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class IO