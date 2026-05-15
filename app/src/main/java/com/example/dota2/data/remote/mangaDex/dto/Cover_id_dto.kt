package com.example.dota2.data.remote.mangaDex.dto

data class CoverArtResponseDto(
    val result: String?,
    val response: String?,
    val data: CoverArtDto?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)