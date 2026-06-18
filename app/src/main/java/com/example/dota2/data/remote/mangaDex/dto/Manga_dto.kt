package com.example.dota2.data.remote.mangaDex.dto

data class MangaListResponseDto(
    val result: String?,
    val response: String?,
    val data: List<MangaDto>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class MangaDto(
    val id: String,
    val type: String?,
    val attributes: MangaAttributesDto?,
    val relationships: List<RelationshipDto>?
)

data class MangaAttributesDto(
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

    val tags: List<TagDto>?,

    val state: String?,

    val version: Int?,

    val createdAt: String?,
    val updatedAt: String?
)

data class TagDto(
    val id: String?,
    val type: String?,
    val attributes: TagAttributesDto?,
    val relationships: List<RelationshipDto>?
)

data class TagAttributesDto(
    val name: Map<String, String>?,
    val description: Map<String, String>?,

    val group: String?,

    val version: Int?
)

