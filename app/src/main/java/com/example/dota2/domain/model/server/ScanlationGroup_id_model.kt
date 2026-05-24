package com.example.dota2.domain.model.server


data class ScanlationGroupListResponseModel(
    val data: List<ScanlationGroupModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class ScanlationGroupModel(
    val id: String?,
    val type: String?,
    val attributes: ScanlationGroupAttributesModel?,
    val relationships: List<RelationshipModel>?
)

data class ScanlationGroupAttributesModel(
    val name: String?,

    val altNames: List<Map<String, String>>?,

    val website: String?,
    val ircServer: String?,
    val ircChannel: String?,
    val discord: String?,
    val contactEmail: String?,
    val description: String?,

    val twitter: String?,
    val mangaUpdates: String?,

    val focusedLanguage: List<String>?,

    val locked: Boolean?,
    val official: Boolean?,
    val verified: Boolean?,
    val inactive: Boolean?,
    val exLicensed: Boolean?,

    val publishDelay: String?, // ISO-8601 duration (e.g. P4D)

    val version: Int?,

    val createdAt: String?,
    val updatedAt: String?
)
