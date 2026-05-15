package com.example.dota2.domain.model.server


data class CoverArtResponseModel(
    val result: String?,
    val response: String?,
    val data: CoverArtModel?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)