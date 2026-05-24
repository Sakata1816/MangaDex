package com.example.dota2.domain.model.server


data class ChapterListResponseModel(
    val data: List<ChapterModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class ChapterModel(
    val id: String?,
    val type: String?,
    val attributes: ChapterAttributesModel?,
    val relationships: List<RelationshipModel>?
)

data class ChapterAttributesModel(
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
