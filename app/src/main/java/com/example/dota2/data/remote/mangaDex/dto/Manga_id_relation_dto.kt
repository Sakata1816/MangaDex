package com.example.dota2.data.remote.mangaDex.dto

data class MangaRelationListResponseDto(
    val result: String?,
    val response: String?,
    val data: List<MangaRelationDto>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class MangaRelationDto(
    val id: String?,
    val type: String?,
    val attributes: MangaRelationAttributesDto?,
    val relationships: List<RelationshipDto>?
)

data class MangaRelationAttributesDto(
    val relation: String?,
    val version: Int?
)

