package com.example.MangaDex.presentation.uiState

import com.example.MangaDex.domain.model.server.MangaModel
import com.example.MangaDex.domain.state.MangaFilters

data class MangaSearchUiState(
    val manga: List<MangaModel> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val page: Int = 0,
    val filter: MangaFilters = MangaFilters(),
    val search: String? = null

)
