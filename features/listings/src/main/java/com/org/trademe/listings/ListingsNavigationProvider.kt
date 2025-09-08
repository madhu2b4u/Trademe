package com.org.trademe.listings

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.org.trademe.core.util.rememberToastState
import com.org.trademe.listings.presentation.LatestListingsScreen
import com.org.trademe.navigation.NavigationProvider
import com.org.trademe.navigation.NavigationRoutes
import javax.inject.Inject

class ListingsNavigationProvider @Inject constructor() : NavigationProvider {

    override fun registerNavigation(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(NavigationRoutes.LISTING_ROUTE) {
            val toastState = rememberToastState()
            val context = LocalContext.current
            LatestListingsScreen(
                onListingClick = { listing ->
                    toastState.showToast(context.getString(R.string.opening_listing, listing.title))
                }
            )
        }
    }
}