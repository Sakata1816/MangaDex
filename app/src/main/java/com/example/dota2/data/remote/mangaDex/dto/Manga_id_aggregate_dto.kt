package com.example.dota2.data.remote.mangaDex.dto

data class MangaAggregateResponseDto(
    val result: String?,
    val volumes: Map<String, VolumeDto>?
)

data class VolumeDto(
    val volume: String?,
    val count: Int?,
    val chapters: Map<String, ChapterAggregateDto>?
)

data class ChapterAggregateDto(
    val chapter: String?,
    val id: String?,

    val isUnavailable: Boolean?,

    val others: List<String>?,

    val count: Int?
)