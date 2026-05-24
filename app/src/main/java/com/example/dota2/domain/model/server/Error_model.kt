package com.example.dota2.domain.model.server

data class ErrorResponseModel(
    val errors: List<ApiErrorModel>?
)

data class ApiErrorModel(
    val id: String?,
    val status: Int?,
    val title: String?,
    val detail: String?,
    val context: String?
)