package com.example.MangaDex.domain.model.profile

data class UserProfileModel(
    val uid: String,
    val email: String?,
    val username: String?,
    val avatarUrl: String?,
)
