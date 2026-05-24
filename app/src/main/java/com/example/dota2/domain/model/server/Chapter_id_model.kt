package com.example.dota2.domain.model.server


data class ChapterResponseModel(
    val data: ChapterModel?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)