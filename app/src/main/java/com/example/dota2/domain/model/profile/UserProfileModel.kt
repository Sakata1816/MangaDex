package com.example.dota2.domain.model.profile

data class UserProfileModel(
    val uid: String,
    val email: String?,
    val username: String?,
    val avatarUrl: String?,
)
