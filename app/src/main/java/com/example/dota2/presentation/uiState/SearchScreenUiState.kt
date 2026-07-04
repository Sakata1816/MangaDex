package com.example.dota2.presentation.uiState

import com.example.dota2.domain.model.server.MangaModel

data class SearchScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val page: Int = 0,
    val endReached: Boolean = false,
    val query: String? = "",
    val manga: List<MangaModel> = emptyList()
)
