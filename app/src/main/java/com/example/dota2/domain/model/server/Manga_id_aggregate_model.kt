package com.example.dota2.domain.model.server

data class MangaAggregateResponseModel(
    val volumes: Map<String, VolumeModel>?
)

data class VolumeModel(
    val volume: String?,
    val count: Int?,
    val chapters: Map<String, ChapterAggregateModel>?
)

data class ChapterAggregateModel(
    val chapter: String?,
    val id: String?,

    val isUnavailable: Boolean?,

    val others: List<String>?,

    val count: Int?
)