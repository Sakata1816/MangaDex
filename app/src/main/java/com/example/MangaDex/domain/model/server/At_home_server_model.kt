package com.example.MangaDex.domain.model.server

data class AtHomeServerModel(
    val result: String?,
    val baseUrl: String?,
    val chapter: ChapterServerModel?

)

data class ChapterServerModel(
    val hash: String?,
    val data: List<String>?,
    val dataSaver: List<String>?
)
