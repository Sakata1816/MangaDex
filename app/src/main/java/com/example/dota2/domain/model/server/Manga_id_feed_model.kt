package com.example.dota2.domain.model.server


data class MangaFeedResponceModel(
    val result: String?,
    val response: String?,
    val data: List<AuthorItemModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)