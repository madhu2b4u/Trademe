package com.org.trademe.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Registry that collects and manages all navigation providers from feature modules
 */
@Singleton
class NavigationRegistry @Inject constructor(
    private val navigationProviders: Set<@JvmSuppressWildcards NavigationProvider>
) {
    /**
     * Registers all navigation routes from all feature modules
     */
    fun registerAllNavigation(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navigationProviders.forEach { provider ->
            provider.registerNavigation(builder, navController)
        }
    }
}