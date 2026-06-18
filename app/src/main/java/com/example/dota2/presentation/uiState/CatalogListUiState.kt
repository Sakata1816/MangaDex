package com.example.dota2.presentation.uiState

import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.state.MangaFilters

data class CatalogListUiState(
    val manga: List<MangaModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0,
    val searchQuery: String = "",
    val filter: MangaFilters = MangaFilters()
)

