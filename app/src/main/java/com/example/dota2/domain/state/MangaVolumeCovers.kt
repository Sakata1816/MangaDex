package com.example.dota2.domain.state

data class MangaVolumeCovers(
    val volume: String? = null,
    val covers: List<String>? = emptyList()
)