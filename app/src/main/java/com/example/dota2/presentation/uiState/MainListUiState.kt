package com.example.dota2.presentation.uiState

import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.model.server.TagModel
import com.example.dota2.domain.state.IncludeType
import com.example.dota2.domain.state.MangaFilters

data class MainListUiState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val manga: List<MangaModel> = emptyList(),
    val endReached: Boolean = false,
    val page: Int = 0,
    val tagsError: String? = null,
    val filters: MangaFilters = MangaFilters()
    )
