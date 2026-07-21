package com.example.MangaDex.domain.model.server


data class CoverArtResponseModel(
    val data: CoverArtModel?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)