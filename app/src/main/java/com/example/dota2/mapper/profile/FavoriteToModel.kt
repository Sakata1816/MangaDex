package com.example.dota2.mapper.profile

import com.example.dota2.data.local.di.toTagModels
import com.example.dota2.data.local.entity.MangaStatus
import com.example.dota2.domain.model.profile.UserFavoriteMangaModel
import com.example.dota2.domain.model.server.MangaAttributesModel
import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.model.server.RelationshipModel

fun UserFavoriteMangaModel.toUi(): MangaModel {
    return MangaModel(
        id = id,
        type = null,
        attributes = MangaAttributesModel(
            title = title,
            altTitles = altTitles,
            description = description,
            isLocked = false,
            links = null,
            officialLinks = null,
            originalLanguage = null,
            lastVolume = lastVolume,
            lastChapter = lastChapter,
            publicationDemographic = publicationDemographic,
            status = status,
            year = year,
            contentRating = contentRating,
            chapterNumbersResetOnNewVolume = null,
            availableTranslatedLanguages = null,
            latestUploadedChapter = latestUploadedChapter,
            tags = tags?.toTagModels(),
            state = state,
            version = null,
            createdAt = createdAt,
            updatedAt = updatedAt

        ),
        relationships = listOf(RelationshipModel(
            id = "",
            type = "cover_art",
            related = null,
            attributes = coverArt
        ))
    )
}


fun MangaModel.toUi(
    userStatus: MangaStatus
): UserFavoriteMangaModel {

    return UserFavoriteMangaModel(
        id = id,

        userStatus = userStatus,

        title = attributes?.title,

        altTitles = attributes?.altTitles,

        coverArt = relationships
            ?.firstOrNull { it.type == "cover_art" }
            ?.attributes as? Map<String, String>,

        description = attributes?.description,

        lastVolume = attributes?.lastVolume,

        lastChapter = attributes?.lastChapter,

        publicationDemographic = attributes?.publicationDemographic,

        status = attributes?.status,

        year = attributes?.year,

        contentRating = attributes?.contentRating,

        latestUploadedChapter = attributes?.latestUploadedChapter,

        tags = attributes?.tags
            ?.mapNotNull { it.attributes?.name?.get("en") },

        state = attributes?.state,

        createdAt = attributes?.createdAt,

        updatedAt = attributes?.updatedAt
    )
}