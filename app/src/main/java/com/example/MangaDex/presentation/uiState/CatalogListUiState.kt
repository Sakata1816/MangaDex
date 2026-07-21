package com.example.MangaDex.presentation.uiState

import com.example.MangaDex.domain.model.server.MangaModel
import com.example.MangaDex.domain.state.MangaFilters

data class CatalogListUiState(
    val manga: List<MangaModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0,
    val searchQuery: String = "",
    val filter: MangaFilters = MangaFilters()
)

