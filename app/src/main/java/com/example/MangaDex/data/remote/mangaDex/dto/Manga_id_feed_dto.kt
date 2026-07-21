package com.example.MangaDex.data.remote.mangaDex.dto

data class MangaFeedResponceDto(
    val result: String?,
    val response: String?,
    val data: List<ChapterDto>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

