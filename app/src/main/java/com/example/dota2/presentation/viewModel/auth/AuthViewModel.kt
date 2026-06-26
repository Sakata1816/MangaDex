package com.example.dota2.presentation.viewModel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota2.domain.repository.auth.AuthRepository
import com.example.dota2.domain.useCase.auth.LoginResult
import com.example.dota2.domain.useCase.auth.LoginUseCase
import com.example.dota2.domain.useCase.auth.RegisterResult
import com.example.dota2.domain.useCase.auth.RegisterUseCase
import com.example.dota2.domain.useCase.profile.EnsureUserProfileUseCase
import com.example.dota2.presentation.navigation.authRoot.AuthState
import com.example.dota2.presentation.uiState.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val repository: AuthRepository,
    private val ensureUserProfileUseCase: EnsureUserProfileUseCase
): ViewModel() {


    private val _authState = MutableStateFlow<AuthState>(AuthState.Authorized)
    val authState = _authState.asStateFlow()

    private val _state = MutableStateFlow(AuthUiState())
    val state = _state.asStateFlow()



    init {
        observeAuthState()
    }

    protected fun observeAuthState(){
        viewModelScope.launch {
            repository.observeAuthState().collect {state->
                _authState.value=state
            }
        }
    }


    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }


    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }


    fun onLoginClick() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, serverError = null) }
            when (val result = loginUseCase(state.value.email, state.value.password)) {
                is LoginResult.Success -> {
                    _state.update { it.copy(isLoading = false, loginSuccess = true) }
                    _authState.update { AuthState.Authorized }
                }
                is LoginResult.ValidationError -> {
                    _state.update { it.copy(
                        isLoading = false,
                        emailError = result.emailError,
                        passwordError = result.passwordError
                    ) }
                }
                is LoginResult.ServerError -> {
                    _state.update { it.copy(isLoading = false, serverError = result.message) }
                }
            }
        }
    }


    fun onRegisterClick() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, serverError = null) }

            when (val result = registerUseCase(state.value.email, state.value.password)) {
                is RegisterResult.Success -> {
                    _state.update { it.copy(isLoading = false, registerSuccess = true) }
                    ensureUserProfileUseCase(result.user.uid, state.value.email)
                    _authState.update { AuthState.Authorized }
                }
                is RegisterResult.ValidationError -> {
                    _state.update { it.copy(
                        isLoading = false,
                        emailError = result.emailError,
                        passwordError = result.passwordError
                    )}
                }
                is RegisterResult.ServerError -> {
                    _state.update { it.copy(isLoading = false, serverError = result.message) }
                }
            }
        }
    }


    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _authState.value = AuthState.Unauthorized
            _state.update { it.copy(
                email = "",
                password = "",
                emailError = null,
                passwordError = null,
                isLoading = false,
                loginSuccess = false,
                registerSuccess = false,
                serverError = null
            ) }
        }
    }


}