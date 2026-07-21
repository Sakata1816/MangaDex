package com.example.MangaDex.domain.model.server


data class MangaFeedResponceModel(
    val data: List<ChapterModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)
