package com.example.dota2.data.remote.mangaDex.dto

data class CoverArtListResponseDto(
    val result: String?,
    val response: String?,
    val data: List<CoverArtDto>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class CoverArtDto(
    val id: String?,
    val type: String?,
    val attributes: CoverArtAttributesDto?,
    val relationships: List<RelationshipDto>?
)

data class CoverArtAttributesDto(
    val volume: String?,
    val fileName: String?,
    val description: String?,
    val locale: String?,

    val version: Int?,

    val createdAt: String?,
    val updatedAt: String?
)
