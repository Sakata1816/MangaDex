package com.example.dota2.domain.model.server


data class CoverArtListResponseModel(
    val result: String?,
    val response: String?,
    val data: List<CoverArtModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class CoverArtModel(
    val id: String?,
    val type: String?,
    val attributes: CoverArtAttributesModel?,
    val relationships: List<RelationshipModel>?
)

data class CoverArtAttributesModel(
    val volume: String?,
    val fileName: String?,
    val description: String?,
    val locale: String?,

    val version: Int?,

    val createdAt: String?,
    val updatedAt: String?
)
