package com.example.dota2.presentation.uiState

import com.example.dota2.domain.model.profile.UserFavoriteMangaModel

data class FavoriteMangaUiState(
    val searchQuery: String = "",
    val watchingList: List<UserFavoriteMangaModel> = emptyList(),
    val completedList: List<UserFavoriteMangaModel> = emptyList(),
    val droppedList: List<UserFavoriteMangaModel> = emptyList(),
    val plannedList: List<UserFavoriteMangaModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)