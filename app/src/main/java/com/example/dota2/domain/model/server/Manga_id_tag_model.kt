package com.example.dota2.domain.model.server


data class TagListResponseModel(
    val result: String?,
    val response: String?,
    val data: List<TagModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)
