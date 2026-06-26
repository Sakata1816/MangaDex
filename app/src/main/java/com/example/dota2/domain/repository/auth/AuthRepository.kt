package com.example.dota2.domain.repository.auth

import com.example.dota2.presentation.navigation.authRoot.AuthState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun observeAuthState(): Flow<AuthState>
    suspend fun login(email: String, password: String): Result<FirebaseUser>

    suspend fun register(email: String, password: String): Result<FirebaseUser>

    fun getCurrentUser(): FirebaseUser?

    fun logout()

}
