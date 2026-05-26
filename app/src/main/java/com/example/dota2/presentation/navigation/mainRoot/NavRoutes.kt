package com.example.dota2.presentation.navigation.mainRoot

sealed class NavRoutes(val route: String){
    object Main: NavRoutes("main_screen")
    object Search: NavRoutes("search_screen")
    object Favorites: NavRoutes("favorites_screen")
    object Settings: NavRoutes("settings_screen")

}