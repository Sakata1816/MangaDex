package com.example.dota2.domain.model.profile

import com.example.dota2.data.local.entity.MangaStatus

data class UserFavoriteMangaModel(
    val id: String,
    val userStatus:MangaStatus,
    val title: Map<String, String>?,
    val altTitles: List<Map<String, String>>?,
    val coverArt: Map<String, String>?,
    val description: Map<String, String>?,
    val lastVolume: String?,
    val lastChapter: String?,
    val publicationDemographic: String?,
    val status: String?,
    val year: Int?,
    val contentRating: String?,
    val latestUploadedChapter: String?,
    val tags: List<String>?,
    val state: String?,
    val createdAt: String?,
    val updatedAt: String?
)