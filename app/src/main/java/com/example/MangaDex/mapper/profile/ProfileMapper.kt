package com.example.MangaDex.mapper.profile

import com.example.MangaDex.data.remote.auth.dto.UserProfileDto
import com.example.MangaDex.domain.model.profile.UserProfileModel

fun UserProfileDto.toModel(): UserProfileModel{
    return UserProfileModel(
        uid = uid,
        email = email,
        username = username,
        avatarUrl = avatarUrl,
    )
}

fun UserProfileModel.toDto(): UserProfileDto{
    return UserProfileDto(
        uid = uid,
        email = email,
        username = username,
        avatarUrl = avatarUrl
    )
}