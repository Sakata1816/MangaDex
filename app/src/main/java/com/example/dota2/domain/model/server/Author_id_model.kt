package com.example.dota2.domain.model.server


data class AuthorResponceModel(
    val data: AuthorItemModel?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)