package com.example.dota2.data.remote.mangaDex.dto

data class AtHomeServerDto(
    val result: String?,
    val baseUrl: String?,
    val chapter: ChapterServerDto?

)

data class ChapterServerDto(
    val hash: String?,
    val data: List<String>?,
    val dataSaver: List<String>?
)
