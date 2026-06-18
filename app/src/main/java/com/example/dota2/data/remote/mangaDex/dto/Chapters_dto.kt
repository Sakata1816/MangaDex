package com.example.dota2.data.remote.mangaDex.dto


data class ChapterListResponseDto(
    val result: String?,
    val response: String?,
    val data: List<ChapterDto>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class ChapterDto(
    val id: String,
    val type: String?,
    val attributes: ChapterAttributesDto?,
    val relationships: List<RelationshipDto>?
)

data class ChapterAttributesDto(
    val title: String?,
    val volume: String?,
    val chapter: String?,

    val pages: Int?,
    val translatedLanguage: String?,

    val uploader: String?,
    val externalUrl: String?,

    val version: Int?,

    val createdAt: String?,
    val updatedAt: String?,
    val publishAt: String?,
    val readableAt: String?,

    val isUnavailable: Boolean?
)
