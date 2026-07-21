package com.example.MangaDex.domain.state

data class MangaVolumeCovers(
    val volume: String? = null,
    val covers: List<String>? = emptyList()
)