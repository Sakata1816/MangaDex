package com.example.dota2.presentation.uiState

import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.state.IncludeType

data class MainListUiState(
    val Error: String? = null,
    val isLoading: Boolean = false,
    val manga: List<MangaModel> = emptyList(),
    val endReached: Boolean = false,
    val page: Int = 0,
    val includes: List<IncludeType>? = null,
    )
