package com.example.dota2.domain.model.server


data class ChapterResponseModel(
    val result: String?,
    val response: String?,
    val data: ChapterModel?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)