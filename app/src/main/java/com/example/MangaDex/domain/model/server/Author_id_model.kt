package com.example.MangaDex.domain.model.server


data class AuthorResponceModel(
    val data: AuthorItemModel?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)