package com.example.dota2.domain.model.server

import com.example.dota2.data.remote.mangaDex.dto.MangaDto

//get random manga /get maNGA BY ID
data class MangaResponseModel(
    val result: String?,
    val response: String?,
    val data: MangaModel?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)