package com.example.dota2.presentation.navigation.mainRoot

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dota2.presentation.screens.MainScreen

@Composable
fun AppNavGraph(navController: NavHostController,modifier: Modifier) {
    NavHost(modifier = modifier, navController=navController, startDestination = NavRoutes.Main.route) {

        composable(NavRoutes.Main.route){
            MainScreen(navController)
        }

        composable(NavRoutes.Search.route){

        }

        composable(NavRoutes.Favorites.route){

        }

        composable(NavRoutes.Settings.route){

        }



    }
}