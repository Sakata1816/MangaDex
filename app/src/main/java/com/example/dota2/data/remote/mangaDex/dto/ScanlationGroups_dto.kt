package com.example.dota2.data.remote.mangaDex.dto

data class ScanlationGroupListResponseDto(
    val result: String?,
    val response: String?,
    val data: List<ScanlationGroupDto>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class ScanlationGroupDto(
    val id: String?,
    val type: String?,
    val attributes: ScanlationGroupAttributesDto?,
    val relationships: List<RelationshipDto>?
)

data class ScanlationGroupAttributesDto(
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

