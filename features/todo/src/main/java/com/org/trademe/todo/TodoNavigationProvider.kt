package com.org.trademe.todo

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.org.trademe.navigation.NavigationProvider
import com.org.trademe.navigation.NavigationRoutes
import com.org.trademe.todo.presentation.TodoScreen
import javax.inject.Inject

/**
 * Navigation provider for Todo feature
 * Registers the todo screen with the navigation graph
 */
class TodoNavigationProvider @Inject constructor() : NavigationProvider {

    override fun registerNavigation(
        builder: NavGraphBuilder,
        navController: NavHostController
    ) {
        builder.composable(NavigationRoutes.TODO_ROUTE) {
            TodoScreen()
        }
    }
}

