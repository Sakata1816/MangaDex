package com.example.MangaDex.mapper.mangaDex

import com.example.MangaDex.data.remote.mangaDex.dto.CoverArtAttributesDto
import com.example.MangaDex.data.remote.mangaDex.dto.CoverArtDto
import com.example.MangaDex.data.remote.mangaDex.dto.CoverArtListResponseDto
import com.example.MangaDex.domain.model.server.CoverArtAttributesModel
import com.example.MangaDex.domain.model.server.CoverArtListResponseModel
import com.example.MangaDex.domain.model.server.CoverArtModel


fun CoverArtListResponseDto.toModel(): CoverArtListResponseModel {
    return CoverArtListResponseModel(
        data = data?.map { it.toModel() },

        limit = limit,
        offset = offset,
        total = total
    )
}


fun CoverArtDto.toModel(): CoverArtModel {
    return CoverArtModel(
        id = id,
        type = type,
        attributes = attributes?.toModel(),
        relationships = relationships?.map { it.toModel() }
    )
}


fun CoverArtAttributesDto.toModel(): CoverArtAttributesModel {
    return CoverArtAttributesModel(
        volume = volume,
        fileName = fileName,
        description = description,
        locale = locale,

        version = version,

        createdAt = createdAt,
        updatedAt = updatedAt
    )
}




fun CoverArtListResponseModel.toDto(): CoverArtListResponseDto{
    return CoverArtListResponseDto(
        result = null,
        response = null,
        data = data?.map { it.toDto() },

        limit = limit,
        offset = offset,
        total = total
    )
}


fun CoverArtModel.toDto():CoverArtDto{
    return CoverArtDto(
        id = id,
        type = type,
        attributes = attributes?.toDto(),
        relationships = relationships?.map { it.toDto() }
    )
}

fun CoverArtAttributesModel.toDto(): CoverArtAttributesDto{
    return CoverArtAttributesDto(
        volume = volume,
        fileName = fileName,
        description = description,
        locale = locale,

        version = version,

        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

