package com.org.trademe

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.org.trademe.navigation.NavigationRegistry
import com.org.trademe.navigation.NavigationRoutes

/**
 * Main navigation host for the application.
 * Delegates navigation registration to the NavigationRegistry.
 *
 * @param modifier Modifier to be applied to the NavHost
 * @param navController NavHostController that controls the navigation
 * @param registry Registry that contains all navigation providers
 */
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    registry: NavigationRegistry
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationRoutes.LISTING_ROUTE
    ) {
        try {
            registry.registerAllNavigation(this, navController)
        } catch (e: Exception) {
            Log.e("AppNavHost", "Error registering navigation routes", e)
        }
    }
}