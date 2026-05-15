package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.CoverArtResponseDto
import com.example.dota2.domain.model.server.CoverArtResponseModel

fun CoverArtResponseDto.toModel(): CoverArtResponseModel {
    return CoverArtResponseModel(
        result = result,
        response = response,
        data = data?.toModel(),

        limit = limit,
        offset = offset,
        total = total
    )
}



fun CoverArtResponseModel.toDto(): CoverArtResponseDto{
    return CoverArtResponseDto(
        result = result,
        response = response,
        data = data?.toDto(),

        limit = limit,
        offset = offset,
        total = total
    )
}
