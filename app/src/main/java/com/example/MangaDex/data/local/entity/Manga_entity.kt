package com.example.MangaDex.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "favorite_manga",
    primaryKeys = ["id"] )
data class FavoriteMangaEntity(
    val id: String,
    val userId: String?,
    val userStatus:MangaStatus,
    val title: Map<String, String>?,
    val altTitles: List<Map<String, String>>?,
    val coverFileName: String?,
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

enum class MangaStatus(val title: String) {
    NONE("Добавить в"),
    PLAN("В планах"),
    READING("Смотрю"),
    DROPPED("Брошено"),
    COMPLETED("Просмотрено"),
    DELETED("Удалить из списка")
}