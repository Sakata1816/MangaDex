package com.example.MangaDex.data.remote.mangaDex.dto

data class TagListResponseDto(
    val result: String?,
    val response: String?,
    val data: List<TagDto>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)
