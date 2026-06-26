package com.example.dota2.domain.useCase.auth

import com.example.dota2.data.repository.auth.AuthRepositoryImpl
import com.example.dota2.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator suspend fun invoke(email: String, password: String): LoginResult {

        val emailError = if (email.isEmpty()) "Email cannot be empty" else null
        val passwordError =
            if (password.length < 6) "Password must be at least 6 characters" else null

        if (emailError != null || passwordError != null || passwordError != null) {
            return LoginResult.ValidationError(emailError, passwordError)
        }

        return repository.login(email, password).fold(
            onSuccess = { LoginResult.Success(it) },
            onFailure = { LoginResult.ServerError(it.message ?: "Unknown error") }
        )
    }

}

sealed class LoginResult {
    data class Success(val user: FirebaseUser) : LoginResult()
    data class ValidationError(val emailError: String?, val passwordError: String?) : LoginResult()
    data class ServerError(val message: String) : LoginResult()
}