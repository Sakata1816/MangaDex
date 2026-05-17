package com.example.dota2.presentation.uiState

import com.example.dota2.domain.model.server.ErrorResponseModel
import com.example.dota2.domain.model.server.MangaListResponseModel
import com.example.dota2.domain.model.server.MangaModel

data class MainListUiState(
    val manga: List<MangaModel> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val page: Int = 0,

)
