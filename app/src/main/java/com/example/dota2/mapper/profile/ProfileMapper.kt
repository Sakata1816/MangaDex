package com.example.dota2.mapper.profile

import com.example.dota2.data.remote.auth.dto.UserProfileDto
import com.example.dota2.domain.model.profile.UserProfileModel

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