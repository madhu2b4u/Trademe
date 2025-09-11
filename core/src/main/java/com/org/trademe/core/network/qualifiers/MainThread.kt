package com.org.trademe.core.network.qualifiers

import javax.inject.Qualifier

/**
 * Qualifier annotation for providing and injecting a MainThread-specific CoroutineContext.
 *
 * This qualifier allows Hilt/Dagger to distinguish between multiple CoroutineContext
 * implementations during dependency injection. When a class requires a dispatcher for
 * UI operations (view updates, animations, user interactions), this qualifier should be used.
 **/

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class MainThread