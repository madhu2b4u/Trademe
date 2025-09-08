package com.org.trademe.watchlist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.org.trademe.navigation.NavigationProvider
import com.org.trademe.navigation.NavigationRoutes
import com.org.trademe.watchlist.presentation.WatchlistScreen
import javax.inject.Inject

class WatchlistNavigationProvider @Inject constructor() : NavigationProvider {

    override fun registerNavigation(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(NavigationRoutes.WATCHLIST_ROUTE) {
            WatchlistScreen()
        }
    }
}