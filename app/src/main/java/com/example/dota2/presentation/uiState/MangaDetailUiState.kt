package com.example.dota2.presentation.uiState

import androidx.loader.content.Loader
import com.example.dota2.domain.model.server.AuthorItemModel
import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.state.MangaVolumeChaptersState


data class MangaDetailUiState(
    val details: MangaModel? = null,
    val chapters: List<MangaVolumeChaptersState>? = null,
    val detailError: String? = null,
    val chapterError: String? = null,
    val mangaIsLoading: Boolean = false,
    val chaptersAreLoading: Boolean = false,
    val page: Int = 0,
    val endReached: Boolean = false
)