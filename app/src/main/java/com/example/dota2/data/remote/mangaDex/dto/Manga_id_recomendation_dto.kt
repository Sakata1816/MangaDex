package com.example.dota2.data.remote.mangaDex.dto

data class MangaRecommendationListResponseDto(
    val result: String?,
    val response: String?,
    val data: List<MangaRecommendationDto>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class MangaRecommendationDto(
    val id: String?,
    val type: String?,
    val attributes: MangaRecommendationAttributesDto?,
    val relationships: List<RelationshipDto>?
)

data class MangaRecommendationAttributesDto(
    val score: Int?
)
