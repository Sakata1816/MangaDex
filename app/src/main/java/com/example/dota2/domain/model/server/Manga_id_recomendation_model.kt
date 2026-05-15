package com.example.dota2.domain.model.server


data class MangaRecommendationListResponseModel(
    val result: String?,
    val response: String?,
    val data: List<MangaRecommendationModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class MangaRecommendationModel(
    val id: String?,
    val type: String?,
    val attributes: MangaRecommendationAttributesModel?,
    val relationships: List<RelationshipModel>?
)

data class MangaRecommendationAttributesModel(
    val score: Int?
)
