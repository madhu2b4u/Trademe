package com.org.trademe.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.org.trademe.navigation.NavigationProvider
import com.org.trademe.navigation.NavigationRoutes
import com.org.trademe.profile.presentation.MyTradeMeScreen
import javax.inject.Inject

class ProfileNavigationProvider @Inject constructor() : NavigationProvider {

    override fun registerNavigation(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(NavigationRoutes.PROFILE_ROUTE) {
            MyTradeMeScreen()
        }
    }
}