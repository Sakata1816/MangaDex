package com.example.MangaDex.mapper.mangaDex

import com.example.MangaDex.data.remote.mangaDex.dto.MangaRecommendationAttributesDto
import com.example.MangaDex.data.remote.mangaDex.dto.MangaRecommendationDto
import com.example.MangaDex.data.remote.mangaDex.dto.MangaRecommendationListResponseDto
import com.example.MangaDex.domain.model.server.MangaRecommendationAttributesModel
import com.example.MangaDex.domain.model.server.MangaRecommendationListResponseModel
import com.example.MangaDex.domain.model.server.MangaRecommendationModel


fun MangaRecommendationListResponseDto.toModel(): MangaRecommendationListResponseModel{
    return MangaRecommendationListResponseModel(
        data = data?.map { it.toModel() },

        limit = limit,
        offset = offset,
        total = total
    )
}

fun MangaRecommendationDto.toModel():MangaRecommendationModel{
    return MangaRecommendationModel(
        id = id,
        type = type,
        attributes = attributes?.toModel(),
        relationships = relationships?.map { it.toModel() }
    )
}


fun MangaRecommendationAttributesDto.toModel(): MangaRecommendationAttributesModel {
    return MangaRecommendationAttributesModel(
        score = score
    )
}




fun MangaRecommendationListResponseModel.toDto(): MangaRecommendationListResponseDto{
    return MangaRecommendationListResponseDto(
        result = null,
        response = null,
        data = data?.map { it.toDto() },

        limit = limit,
        offset = offset,
        total = total
    )
}

fun MangaRecommendationModel.toDto(): MangaRecommendationDto{
    return MangaRecommendationDto(
        id = id,
        type = type,
        attributes = attributes?.toDto(),
        relationships = relationships?.map { it.toDto() }
    )
}


fun MangaRecommendationAttributesModel.toDto(): MangaRecommendationAttributesDto{
    return MangaRecommendationAttributesDto(
        score = score
    )
}