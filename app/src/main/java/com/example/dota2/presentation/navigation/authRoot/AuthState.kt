package com.example.dota2.presentation.navigation.authRoot

sealed class AuthState {
    object Authorized : AuthState()
    object Unauthorized : AuthState()

}