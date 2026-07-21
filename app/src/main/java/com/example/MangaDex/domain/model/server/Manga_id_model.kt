package com.example.MangaDex.domain.model.server

//get random manga /get maNGA BY ID
data class MangaResponseModel(
    val data: MangaModel?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)