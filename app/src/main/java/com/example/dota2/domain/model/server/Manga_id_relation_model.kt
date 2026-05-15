package com.example.dota2.domain.model.server


data class MangaRelationListResponseModel(
    val result: String?,
    val response: String?,
    val data: List<MangaRelationModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class MangaRelationModel(
    val id: String?,
    val type: String?,
    val attributes: MangaRelationAttributesModel?,
    val relationships: List<RelationshipModel>?
)

data class MangaRelationAttributesModel(
    val relation: String?,
    val version: Int?
)