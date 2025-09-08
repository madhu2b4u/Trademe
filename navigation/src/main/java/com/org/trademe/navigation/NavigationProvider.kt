package com.org.trademe.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * Interface to be implemented by feature modules to provide navigation destinations
 */
interface NavigationProvider {
    /**
     * Registers navigation routes from a feature module
     * @param builder The NavGraphBuilder to register routes with
     * @param navController The NavHostController to use for navigation
     */
    fun registerNavigation(
        builder: NavGraphBuilder,
        navController: NavHostController
    )
}