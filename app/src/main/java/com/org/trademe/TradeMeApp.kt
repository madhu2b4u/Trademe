package com.org.trademe

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.org.trademe.core.theme.TradeMeColors
import com.org.trademe.core.util.rememberToastState
import com.org.trademe.navigation.NavigationRegistry
import com.org.trademe.navigation.NavigationRoutes

data class TabItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradeMeApp(
    navigationRegistry: NavigationRegistry
) {
    val navController = rememberNavController()
    var selectedTab by remember { mutableIntStateOf(0) }
    val toastState = rememberToastState()
    val context = LocalContext.current

    val tabs = listOf(
        TabItem(
            stringResource(R.string.latest),
            Icons.Default.List,
            NavigationRoutes.LISTING_ROUTE
        ),
        TabItem(
            stringResource(R.string.watchlist),
            Icons.Default.Favorite,
            NavigationRoutes.WATCHLIST_ROUTE
        ),
        TabItem(
            stringResource(R.string.my_trade_me),
            Icons.Default.Person,
            NavigationRoutes.PROFILE_ROUTE
        )
    )

    // Handle navigation when tab changes ent
    LaunchedEffect(selectedTab) {
        val route = tabs[selectedTab].route
        navController.navigate(route) {
            // Clear back stack to prevent multiple instances
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (selectedTab) {
                            0 -> stringResource(R.string.latest_listings)
                            1 -> stringResource(R.string.watchlist)
                            2 -> stringResource(R.string.my_trade_me)
                            else -> stringResource(R.string.trade_me)
                        },
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                actions = {
                    if (selectedTab == 0) {
                        IconButton(
                            onClick = {
                                toastState.showToast(context.getString(R.string.search_functionality_coming_soon))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = {
                                toastState.showToast(context.getString(R.string.cart_functionality_coming_soon))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TradeMeColors.Tasman500
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                tab.icon,
                                contentDescription = tab.title,
                                tint = if (selectedTab == index) TradeMeColors.Tasman500
                                else TradeMeColors.BluffOyster600
                            )
                        },
                        label = {
                            Text(
                                tab.title,
                                color = if (selectedTab == index) TradeMeColors.Tasman500
                                else TradeMeColors.BluffOyster600
                            )
                        },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            registry = navigationRegistry
        )
    }
}