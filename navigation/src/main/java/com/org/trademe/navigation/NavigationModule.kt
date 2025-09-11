package com.org.trademe.navigation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Purpose of NavigationModule
 * This module provides a centralized way to collect and manage navigation providers from various feature modules in your TradeMe application. Specifically:
 *
 * Dependency Injection: It uses Hilt's @Module and @InstallIn annotations to register the navigation components at the singleton scope,ensuring consistent navigation across the app.
 * Centralized Navigation Management: By providing a singleton NavigationRegistry, it creates a single point of coordination for all navigation between features.
 * Modular Architecture Support: This pattern enables feature modules to define their own navigation routes independently while still integrating with the app's overall navigation system.
 * Decoupling Features: Each feature module can implement the NavigationProvider interface without knowing about other features, promoting a clean separation of concerns.
 * Scalability: As your app grows with more features, new modules can simply provide their own NavigationProvider implementations which will be automatically collected through Hilt's multibinding.
 * The @JvmSuppressWildcards annotation is used to handle Kotlin's type variance when working with Java generics in the Dagger/Hilt framework.
 */

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideNavigationRegistry(
        navigationProviders: Set<@JvmSuppressWildcards NavigationProvider>
    ): NavigationRegistry {
        return NavigationRegistry(navigationProviders)
    }
}