package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.MangaRecommendationAttributesDto
import com.example.dota2.data.remote.mangaDex.dto.MangaRecommendationDto
import com.example.dota2.data.remote.mangaDex.dto.MangaRecommendationListResponseDto
import com.example.dota2.domain.model.server.MangaRecommendationAttributesModel
import com.example.dota2.domain.model.server.MangaRecommendationListResponseModel
import com.example.dota2.domain.model.server.MangaRecommendationModel


fun MangaRecommendationListResponseDto.toModel(): MangaRecommendationListResponseModel{
    return MangaRecommendationListResponseModel(
        result = result,
        response = response,
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
        result = result,
        response = response,
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