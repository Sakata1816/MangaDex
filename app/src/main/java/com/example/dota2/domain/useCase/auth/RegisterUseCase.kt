package com.example.dota2.domain.useCase.auth

import android.util.Patterns
import com.example.dota2.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): RegisterResult {

        val emailError = if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            "Неверный формат email" else null
        val passwordError = if (password.length < 6)
            "Пароль минимум 6 символов" else null

        if (emailError != null || passwordError != null) {
            return RegisterResult.ValidationError(emailError, passwordError)
        }

        return repository.register(email, password).fold(
            onSuccess = { RegisterResult.Success(it) },
            onFailure = { RegisterResult.ServerError(it.message ?: "Ошибка регистрации") }
        )
    }
}

sealed class RegisterResult {
    data class Success(val user: FirebaseUser) : RegisterResult()
    data class ValidationError(val emailError: String?, val passwordError: String?) : RegisterResult()
    data class ServerError(val message: String) : RegisterResult()
}