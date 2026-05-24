package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.MangaRelationAttributesDto
import com.example.dota2.data.remote.mangaDex.dto.MangaRelationDto
import com.example.dota2.data.remote.mangaDex.dto.MangaRelationListResponseDto
import com.example.dota2.domain.model.server.MangaRelationAttributesModel
import com.example.dota2.domain.model.server.MangaRelationListResponseModel
import com.example.dota2.domain.model.server.MangaRelationModel


fun MangaRelationListResponseDto.toModel(): MangaRelationListResponseModel{
    return MangaRelationListResponseModel(
        data = data?.map { it.toModel() },

        limit = limit,
        offset = offset,
        total = total
    )
}

fun MangaRelationDto.toModel(): MangaRelationModel{
    return MangaRelationModel(
        id = id,
        type = type,
        attributes = attributes?.toModel(),
        relationships = relationships?.map { it.toModel() }
    )
}

fun MangaRelationAttributesDto.toModel():MangaRelationAttributesModel{
    return MangaRelationAttributesModel(
        relation = relation,
        version = version
    )
}




fun MangaRelationListResponseModel.toDto(): MangaRelationListResponseDto{
    return MangaRelationListResponseDto(
        result = null,
        response = null,
        data = data?.map { it.toDto() },

        limit = limit,
        offset = offset,
        total = total
    )
}

fun MangaRelationModel.toDto(): MangaRelationDto{
    return MangaRelationDto(
        id = id,
        type = type,
        attributes = attributes?.toDto(),
        relationships = relationships?.map { it.toDto() }
    )
}

fun MangaRelationAttributesModel.toDto(): MangaRelationAttributesDto{
    return MangaRelationAttributesDto(
        relation = relation,
        version = version
    )
}