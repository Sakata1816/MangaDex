package com.example.dota2.data.remote.mangaDex.dto

data class AuthorResponceDto(
    val result: String?,
    val response: String?,
    val data: AuthorItemDto?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)