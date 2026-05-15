package com.example.dota2.data.remote.mangaDex.dto


//get random manga /get maNGA BY ID
data class MangaResponseDto(
    val result: String?,
    val response: String?,
    val data: MangaDto?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)