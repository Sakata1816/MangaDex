package com.example.dota2.data.remote.mangaDex.dto

data class MangaFeedResponceDto(
    val result: String?,
    val response: String?,
    val data: List<ChapterDto>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

