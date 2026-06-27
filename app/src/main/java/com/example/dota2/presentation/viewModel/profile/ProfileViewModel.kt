package com.example.dota2.presentation.viewModel.profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota2.domain.model.profile.UserProfileModel
import com.example.dota2.domain.repository.auth.AuthRepository
import com.example.dota2.domain.repository.profile.ProfileRepository
import com.example.dota2.domain.useCase.profile.EnsureUserProfileUseCase
import com.example.dota2.domain.useCase.profile.UpdateUserProfileUseCase
import com.example.dota2.mapper.profile.toDto
import com.example.dota2.presentation.uiState.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val authRepository: AuthRepository
    ): ViewModel(){

        private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    var profile by mutableStateOf<UserProfileModel?>(null)
        private set

    var isSaved by mutableStateOf(false)
        private set




    fun loadProfile() {
        val uid = authRepository.getCurrentUser()?.uid

        if (uid == null) {
            _state.update {it.copy(error = "Пользователь не авторизован") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = repository.getUser(uid).fold(
                onSuccess = {respose->
                    if (respose != null) {
                        _state.update { it.copy(
                            isLoading = false,
                            error = null,
                            profile = respose
                        ) }
                    } else {
                        _state.update { it.copy(error = "Профиль не найден") }
                    }
                },
                onFailure = {throwable ->
                    _state.update { it.copy(error = throwable.message?:"Ошибка загрузки профиля") }
                }
            )
        }
    }



    fun createProfile(profile: UserProfileModel) {
        viewModelScope.launch {
            repository.createUser(profile.toDto())
            this@ProfileViewModel._state.update { it.copy(profile=profile) }
        }
    }


    fun updateProfile(username: String, uri: Uri?) {
        viewModelScope.launch {
            val uid = authRepository.getCurrentUser()?.uid ?:return@launch
            _state.update { it.copy(isLoading = true) }

            try {
                val avatarUrl = if (uri != null) {
                    repository.uploadAvatar(uid, uri).getOrThrow()
                } else {
                    state.value.profile?.avatarUrl
                }
                updateUserProfileUseCase(uid, username, avatarUrl)
                isSaved = true


                //change
                _state.update { it.copy(
                    profile= profile?.copy(username = username,
                        avatarUrl = avatarUrl),
                    isLoading = false)
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message?:"Ошибка обновления профиля", isLoading = false) }
            }
        }
    }

    fun resetState(){
        isSaved = false
    }


}