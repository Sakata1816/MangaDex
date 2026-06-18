package com.example.dota2.presentation.navigation.mainRoot

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainRoot(){

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val NavBarRoutes = listOf(
        NavRoutes.Main.route,
        NavRoutes.Catalog.route,
        NavRoutes.Favorites.route,
        NavRoutes.Settings.route
    )

    val showBar = NavBarRoutes.any { routes ->
        currentRoute == routes || currentRoute?.startsWith("settings/")==true
    }

    Scaffold(
        bottomBar = {
            if(showBar){
                NavigationBar(navController = navController, currentRoute = currentRoute?:"")
            }
        }
    ){
        AppNavGraph(navController = navController, modifier = Modifier.padding(it))
    }

}


@Composable
fun NavigationBar(
    navController: NavController,
    currentRoute: String)
{
    NavigationBar() {

        NavigationBarItem(
            selected = currentRoute == NavRoutes.Main.route,
            onClick = {
                navController.navigate(NavRoutes.Main.route){
                    popUpTo(NavRoutes.Main.route){
                        saveState = true
                        inclusive = false
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Main") }
        )

        NavigationBarItem(
            selected = currentRoute == NavRoutes.Favorites.route,
            onClick = {
                navController.navigate(NavRoutes.Favorites.route){
                    popUpTo(NavRoutes.Main.route){
                        saveState = true
                        inclusive = false
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Favorite, null) },
            label = { Text("Favorites") }

        )

        NavigationBarItem(
            selected = currentRoute == NavRoutes.Catalog.route,
            onClick = {
                navController.navigate(NavRoutes.CatalogGraph.route){
                    popUpTo(NavRoutes.Main.route){
                        saveState = true
                        inclusive = false
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Search, null) },
            label = { Text("Search") }
        )

        NavigationBarItem(
            selected = currentRoute == NavRoutes.Settings.route,
            onClick = {
                navController.navigate(NavRoutes.Settings.route){
                    popUpTo(NavRoutes.Main.route){
                        saveState = true
                        inclusive = false
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Settings, null) },
            label = { Text("Settings") }
        )

    }


}