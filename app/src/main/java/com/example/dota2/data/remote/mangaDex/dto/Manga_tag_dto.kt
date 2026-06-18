package com.example.dota2.data.remote.mangaDex.dto

data class TagListResponseDto(
    val result: String?,
    val response: String?,
    val data: List<TagDto>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)
