package com.example.MangaDex.domain.model.server


data class TagListResponseModel(
    val data: List<TagModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)
