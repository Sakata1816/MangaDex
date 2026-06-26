package com.example.dota2.presentation.uiState

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val serverError: String? = null,
    val loginSuccess: Boolean = false,
    val registerSuccess: Boolean = false
) {
    val isButtonEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && !isLoading
}
