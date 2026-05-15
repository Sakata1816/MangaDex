package com.example.dota2.data.remote.mangaDex.dto


data class ErrorResponseDto(
    val result: String?,
    val errors: List<ApiErrorDto>?
)

data class ApiErrorDto(
    val id: String?,
    val status: Int?,
    val title: String?,
    val detail: String?,
    val context: String?
)