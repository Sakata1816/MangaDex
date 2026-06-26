package com.example.dota2.mapper.profile

import com.example.dota2.data.local.entity.FavoriteMangaEntity
import com.example.dota2.data.local.entity.MangaStatus
import com.example.dota2.data.remote.auth.dto.UserFavoriteMangaDto
import com.example.dota2.domain.model.profile.UserFavoriteMangaModel

fun UserFavoriteMangaDto.toModel(): UserFavoriteMangaModel{
    return UserFavoriteMangaModel(
        id = id,
        userStatus = userStatus,
        title = title,
        altTitles = altTitles,
        coverArt = coverArt,
        description = description,
        lastVolume = lastVolume,
        lastChapter = lastChapter,
        publicationDemographic = publicationDemographic,
        status = status,
        year = year,
        contentRating = contentRating,
        latestUploadedChapter = latestUploadedChapter,
        tags = tags,
        state = state,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}


fun UserFavoriteMangaModel.toDto(): UserFavoriteMangaDto{
    return UserFavoriteMangaDto(
        id = id,
        userStatus = userStatus,
        title = title,
        altTitles = altTitles,
        coverArt = coverArt,
        description = description,
        lastVolume = lastVolume,
        lastChapter = lastChapter,
        publicationDemographic = publicationDemographic,
        status = status,
        year = year,
        contentRating = contentRating,
        latestUploadedChapter = latestUploadedChapter,
        tags = tags,
        state = state,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}


fun FavoriteMangaEntity.toDomain(): UserFavoriteMangaModel{
    return UserFavoriteMangaModel(
        id = id,
        userStatus = userStatus,
        title = title,
        altTitles = altTitles,
        coverArt = coverArt,
        description = description,
        lastVolume = lastVolume,
        lastChapter = lastChapter,
        publicationDemographic = publicationDemographic,
        status = status,
        year = year,
        contentRating = contentRating,
        latestUploadedChapter = latestUploadedChapter,
        tags = tags,
        state = state,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}


fun UserFavoriteMangaModel.toEntity(userId: String): FavoriteMangaEntity{
    return FavoriteMangaEntity(
        id = id,
        userId = userId,
        userStatus = userStatus,
        title = title,
        altTitles = altTitles,
        coverArt = coverArt,
        description = description,
        lastVolume = lastVolume,
        lastChapter = lastChapter,
        publicationDemographic = publicationDemographic,
        status = status,
        year = year,
        contentRating = contentRating,
        latestUploadedChapter = latestUploadedChapter,
        tags = tags,
        state = state,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}