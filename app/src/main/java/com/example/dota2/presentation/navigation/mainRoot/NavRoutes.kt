package com.example.dota2.presentation.navigation.mainRoot

sealed class NavRoutes(val route: String){
    object Main: NavRoutes("main_screen")

}