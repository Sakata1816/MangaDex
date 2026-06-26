package com.example.dota2.presentation.uiState

import com.example.dota2.domain.model.profile.UserProfileModel

data class ProfileUiState(
    val isLoading:Boolean = false,
    val error: String? = null,
    val profile: UserProfileModel? = null
)
