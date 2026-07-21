package com.example.MangaDex.data.remote.auth.dto

data class UserProfileDto(
    val uid: String="",
    val email: String?="",
    val username: String? = "",
    val avatarUrl: String? = "",
    val favorites: List<UserFavoriteMangaDto>? = emptyList()
)
