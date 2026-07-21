package com.example.MangaDex.presentation.navigation.mainRoot

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.MangaDex.material_theme.customComponents.AppBottomBar
import com.example.MangaDex.material_theme.customComponents.BottomBarItem
import androidx.compose.material3.Scaffold


@Composable
fun MainRoot() {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.orEmpty()

    val showBottomBar = currentRoute in listOf(
        NavRoutes.Main.route,
        NavRoutes.Catalog.route,
        NavRoutes.Favorites.route,
        NavRoutes.Settings.route
    )

    val items = remember {
        listOf(
            BottomBarItem(
                route = NavRoutes.Main.route,
                title = "Home",
                icon = Icons.Default.Home
            ),
            BottomBarItem(
                route = NavRoutes.CatalogGraph.route,
                title = "Catalog",
                icon = Icons.Default.Search
            ),
            BottomBarItem(
                route = NavRoutes.Favorites.route,
                title = "Favorites",
                icon = Icons.Default.Favorite
            ),
            BottomBarItem(
                route = NavRoutes.Settings.route,
                title = "Settings",
                icon = Icons.Default.Settings
            )
        )
    }

    Scaffold(

        bottomBar = {

            if (showBottomBar) {

                AppBottomBar(
                    currentRoute = currentRoute,
                    items = items
                ) { route ->

                    navController.navigate(route) {

                        popUpTo(NavRoutes.Main.route) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }

                }

            }

        }

    ) { padding ->

        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        )

    }

}