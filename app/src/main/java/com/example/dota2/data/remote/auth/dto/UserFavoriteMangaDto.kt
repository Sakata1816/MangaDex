package com.example.dota2.data.remote.auth.dto

import com.example.dota2.data.local.entity.MangaStatus

data class UserFavoriteMangaDto(
    val id: String = "",
    val userStatus: MangaStatus = MangaStatus.NONE, // или другое значение по умолчанию
    val title: Map<String, String>? = null,
    val altTitles: List<Map<String, String>>? = null,
    val coverFileName: String? = null,
    val description: Map<String, String>? = null,
    val lastVolume: String? = null,
    val lastChapter: String? = null,
    val publicationDemographic: String? = null,
    val status: String? = null,
    val year: Int? = null,
    val contentRating: String? = null,
    val latestUploadedChapter: String? = null,
    val tags: List<String>? = null,
    val state: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)