package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.MangaAttributesDto
import com.example.dota2.data.remote.mangaDex.dto.MangaDto
import com.example.dota2.data.remote.mangaDex.dto.MangaListResponseDto
import com.example.dota2.data.remote.mangaDex.dto.TagAttributesDto
import com.example.dota2.data.remote.mangaDex.dto.TagDto
import com.example.dota2.domain.model.server.MangaAttributesModel
import com.example.dota2.domain.model.server.MangaListResponseModel
import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.model.server.TagAttributesModel
import com.example.dota2.domain.model.server.TagModel



fun MangaListResponseDto.toModel(): MangaListResponseModel {
    return MangaListResponseModel(
        data = data?.map { it.toModel() },

        limit = limit,
        offset = offset,
        total = total
    )
}


fun MangaDto.toModel(): MangaModel {
    return MangaModel(
        id = id,
        type = type,
        attributes = attributes?.toModel(),
        relationships = relationships?.map { it.toModel() }
    )
}


fun MangaAttributesDto.toModel(): MangaAttributesModel {
    return MangaAttributesModel(
        title = title,
        altTitles = altTitles,
        description = description,

        isLocked = isLocked,

        links = links,
        officialLinks = officialLinks,

        originalLanguage = originalLanguage,

        lastVolume = lastVolume,
        lastChapter = lastChapter,

        publicationDemographic = publicationDemographic,
        status = status,

        year = year,

        contentRating = contentRating,

        chapterNumbersResetOnNewVolume = chapterNumbersResetOnNewVolume,

        availableTranslatedLanguages = availableTranslatedLanguages,

        latestUploadedChapter = latestUploadedChapter,

        tags = tags?.map { it.toModel() },

        state = state,

        version = version,

        createdAt = createdAt,
        updatedAt = updatedAt
    )
}


fun TagDto.toModel():TagModel{
    return TagModel(
        id = id,
        type = type,
        attributes = attributes?.toModel(),
        relationships = relationships?.map { it.toModel() }
    )
}


fun TagAttributesDto.toModel(): TagAttributesModel{
    return TagAttributesModel(
        name = name,
        description = description,

        group = group,

        version = version
    )
}




fun MangaListResponseModel.toDto(): MangaListResponseDto {
    return MangaListResponseDto(
        result = null,
        response = null,
        data = data?.map { it.toDto() },

        limit = limit,
        offset = offset,
        total = total
    )
}


fun MangaModel.toDto(): MangaDto {
    return MangaDto(
        id = id,
        type = type,
        attributes = attributes?.toDto(),
        relationships = relationships?.map { it.toDto() }
    )
}


fun MangaAttributesModel.toDto(): MangaAttributesDto {
    return MangaAttributesDto(
        title = title,
        altTitles = altTitles,
        description = description,

        isLocked = isLocked,

        links = links,
        officialLinks = officialLinks,

        originalLanguage = originalLanguage,

        lastVolume = lastVolume,
        lastChapter = lastChapter,

        publicationDemographic = publicationDemographic,
        status = status,

        year = year,

        contentRating = contentRating,

        chapterNumbersResetOnNewVolume = chapterNumbersResetOnNewVolume,

        availableTranslatedLanguages = availableTranslatedLanguages,

        latestUploadedChapter = latestUploadedChapter,

        tags = tags?.map { it.toDto() },

        state = state,

        version = version,

        createdAt = createdAt,
        updatedAt = updatedAt
    )
}


fun TagModel.toDto(): TagDto{
    return TagDto(
        id = id,
        type = type,
        attributes = attributes?.toDto(),
        relationships = relationships?.map { it.toDto() }
    )
}


fun TagAttributesModel.toDto(): TagAttributesDto{
    return TagAttributesDto(
        name = name,
        description = description,

        group = group,

        version = version
    )
}