package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.ScanlationGroupAttributesDto
import com.example.dota2.data.remote.mangaDex.dto.ScanlationGroupDto
import com.example.dota2.data.remote.mangaDex.dto.ScanlationGroupListResponseDto
import com.example.dota2.domain.model.server.ScanlationGroupAttributesModel
import com.example.dota2.domain.model.server.ScanlationGroupListResponseModel
import com.example.dota2.domain.model.server.ScanlationGroupModel





fun ScanlationGroupListResponseDto.toModel(): ScanlationGroupListResponseModel{
    return ScanlationGroupListResponseModel(
        data = data?.map { it.toModel() },

        limit = limit,
        offset = offset,
        total = total
    )
}


fun ScanlationGroupDto.toModel(): ScanlationGroupModel {
    return ScanlationGroupModel(
        id = id,
        type = type,
        attributes = attributes?.toModel(),
        relationships = relationships?.map { it.toModel() }
    )
}


fun ScanlationGroupAttributesDto.toModel(): ScanlationGroupAttributesModel {
    return ScanlationGroupAttributesModel(
        name = name,

        altNames = altNames,

        website = website,
        ircServer = ircServer,
        ircChannel = ircChannel,
        discord = discord,
        contactEmail = contactEmail,
        description = description,

        twitter = twitter,
        mangaUpdates = mangaUpdates,

        focusedLanguage = focusedLanguage,

        locked = locked,
        official = official,
        verified = verified,
        inactive = inactive,
        exLicensed = exLicensed,

        publishDelay = publishDelay,

        version = version,

        createdAt = createdAt,
        updatedAt = updatedAt
    )
}




fun ScanlationGroupListResponseModel.toDto(): ScanlationGroupListResponseDto{
    return ScanlationGroupListResponseDto(
        result = null,
        response = null,
        data = data?.map { it.toDto() },

        limit = limit,
        offset = offset,
        total = total
    )
}


fun ScanlationGroupModel.toDto(): ScanlationGroupDto {
    return ScanlationGroupDto(
        id = id,
        type = type,
        attributes = attributes?.toDto(),
        relationships = relationships?.map { it.toDto() }
    )
}


fun ScanlationGroupAttributesModel.toDto(): ScanlationGroupAttributesDto {
    return ScanlationGroupAttributesDto(
        name = name,

        altNames = altNames,

        website = website,
        ircServer = ircServer,
        ircChannel = ircChannel,
        discord = discord,
        contactEmail = contactEmail,
        description = description,

        twitter = twitter,
        mangaUpdates = mangaUpdates,

        focusedLanguage = focusedLanguage,

        locked = locked,
        official = official,
        verified = verified,
        inactive = inactive,
        exLicensed = exLicensed,

        publishDelay = publishDelay,

        version = version,

        createdAt = createdAt,
        updatedAt = updatedAt
    )
}