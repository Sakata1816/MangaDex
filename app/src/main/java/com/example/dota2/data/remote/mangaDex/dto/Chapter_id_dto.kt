package com.example.dota2.data.remote.mangaDex.dto

data class ChapterResponseDto(
    val result: String?,
    val response: String?,
    val data: ChapterDto?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)