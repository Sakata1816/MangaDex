package com.example.MangaDex.domain.state

import com.example.MangaDex.domain.model.server.ChapterModel

data class MangaVolumeChaptersState(
    val chapter: String? = null,
    val title: String? = null,
    val translations: List<ChapterModel> = emptyList(),
)
