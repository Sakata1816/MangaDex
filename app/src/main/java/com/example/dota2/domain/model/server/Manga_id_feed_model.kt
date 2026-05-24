package com.example.dota2.domain.model.server


data class MangaFeedResponceModel(
    val data: List<ChapterModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)
