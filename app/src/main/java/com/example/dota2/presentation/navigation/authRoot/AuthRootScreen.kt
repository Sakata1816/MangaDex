package com.example.dota2.presentation.navigation.authRoot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dota2.presentation.navigation.mainRoot.MainRoot
import com.example.dota2.presentation.screens.auth.LoginScreen
import com.example.dota2.presentation.screens.auth.RegisterScreen
import com.example.dota2.presentation.viewModel.auth.AuthViewModel


@Composable
fun RootScreen(
     viewModel: AuthViewModel = hiltViewModel()
){
    val authState by viewModel.authState.collectAsState()

    when (authState) {
        AuthState.Authorized -> MainRoot()
        AuthState.Unauthorized -> AuthRoot()

    }

}


@Composable
fun AuthRoot(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            LoginScreen(onNavigateToRegister = {navController.navigate("register")})
        }
        composable("register"){
            RegisterScreen(onNavigateToLogin = {navController.navigate("login")})
        }
        }
}