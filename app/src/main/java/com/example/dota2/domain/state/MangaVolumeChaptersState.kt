package com.example.dota2.domain.state

import com.example.dota2.domain.model.server.ChapterModel

data class MangaVolumeChaptersState(
    val volume: String? = null,
    val chapters: List<ChapterModel> = emptyList(),
)
