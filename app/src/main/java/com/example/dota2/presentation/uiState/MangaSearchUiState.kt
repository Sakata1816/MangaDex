package com.example.dota2.presentation.uiState

import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.state.MangaFilters

data class MangaSearchUiState(
    val manga: List<MangaModel> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val page: Int = 0,
    val filter: MangaFilters = MangaFilters(),
    val search: String? = null

)
