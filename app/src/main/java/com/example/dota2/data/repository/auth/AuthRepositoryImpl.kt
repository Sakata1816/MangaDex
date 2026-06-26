package com.example.dota2.data.repository.auth

import android.content.ContentValues
import android.util.Log
import com.example.dota2.data.dataSource.fireBase.AuthDataSource
import com.example.dota2.domain.repository.auth.AuthRepository
import com.example.dota2.presentation.navigation.authRoot.AuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
): AuthRepository {

    // Метод для получения флоу авторизован или нет
    override suspend fun observeAuthState(): Flow<AuthState> {
        return callbackFlow {
            val auth = FirebaseAuth.getInstance()

            val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                val state = if (firebaseAuth.currentUser != null) {
                    AuthState.Authorized
                } else {
                    AuthState.Unauthorized
                }

                trySend(state) // отправляем значение в Flow
            }

            auth.addAuthStateListener(listener)

            awaitClose {
                auth.removeAuthStateListener(listener)
            }
        }
    }

    override suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = dataSource.login(email, password)
            Result.success(result.user!!)
        }catch (e: Exception){
            Result.failure(mapError(e))
        }
    }


    override suspend fun register(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = dataSource.register(email, password)
            Result.success(result.user!!)
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "register failed", e)
            Result.failure(mapError(e))
        }
    }

    override fun getCurrentUser() = dataSource.getCurrentUser()

    override fun logout() = dataSource.logout()

    private fun mapError(e: Exception): Exception {
        return Exception(
            when (e.message) {
                "The email address is badly formatted." -> "Неверный email"
                "The password is invalid or the user does not have a password." -> "Неверный пароль"
                else -> "Ошибка авторизации"
            }
        )
    }

}
