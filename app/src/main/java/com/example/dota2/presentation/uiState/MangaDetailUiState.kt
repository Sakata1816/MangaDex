package com.example.dota2.presentation.uiState


import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.state.MangaVolumeChaptersState
import com.example.dota2.domain.state.MangaVolumeCovers


data class MangaDetailScreenState(
    // Detail
    val manga: MangaModel? = null,
    val detailLoading: Boolean = false,
    val detailError: String? = null,

    // Chapters
    val chapters: List<MangaVolumeChaptersState> = emptyList(),
    val chaptersLoading: Boolean = false,
    val chaptersError: String? = null,
    val chaptersPage: Int = 0,
    val chaptersEndReached: Boolean = false,

    // Relations
    val relations: List<MangaModel> = emptyList(),
    val relationsLoading: Boolean = false,
    val relationsError: String? = null,
    val relationsPage: Int = 0,
    val relationsEndReached: Boolean = false,

    //Covers
    val covers: List<MangaVolumeCovers> = emptyList(),
    val coversLoading: Boolean = false,
    val coversError: String? = null,
    val coversPage: Int = 0,
    val coversEndReached: Boolean = false,
)

