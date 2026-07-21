package com.example.MangaDex.presentation.uiState

import com.example.MangaDex.domain.model.profile.UserProfileModel

data class ProfileUiState(
    val isLoading:Boolean = false,
    val error: String? = null,
    val profile: UserProfileModel? = null
)
