package com.example.dota2.domain.model.server


data class MangaListResponseModel(
    val data: List<MangaModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class MangaModel(
    val id: String,
    val type: String?,
    val attributes: MangaAttributesModel?,
    val relationships: List<RelationshipModel>?
)

data class MangaAttributesModel(
    val title: Map<String, String>?,
    val altTitles: List<Map<String, String>>?,
    val description: Map<String, String>?,

    val isLocked: Boolean?,

    val links: Map<String, String>?,
    val officialLinks: Map<String, String>?,

    val originalLanguage: String?,

    val lastVolume: String?,
    val lastChapter: String?,

    val publicationDemographic: String?,
    val status: String?,

    val year: Int?,

    val contentRating: String?,

    val chapterNumbersResetOnNewVolume: Boolean?,

    val availableTranslatedLanguages: List<String>?,

    val latestUploadedChapter: String?,

    val tags: List<TagModel>?,

    val state: String?,

    val version: Int?,

    val createdAt: String?,
    val updatedAt: String?
)

data class TagModel(
    val id: String?,
    val type: String?,
    val attributes: TagAttributesModel?,
    val relationships: List<RelationshipModel>?
)

data class TagAttributesModel(
    val name: Map<String, String>?,
    val description: Map<String, String>?,

    val group: String?,

    val version: Int?
)