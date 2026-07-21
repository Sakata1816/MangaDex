package com.example.MangaDex.mapper.mangaDex

import com.example.MangaDex.data.remote.mangaDex.dto.CoverArtResponseDto
import com.example.MangaDex.domain.model.server.CoverArtResponseModel

fun CoverArtResponseDto.toModel(): CoverArtResponseModel {
    return CoverArtResponseModel(
        data = data?.toModel(),

        limit = limit,
        offset = offset,
        total = total
    )
}



fun CoverArtResponseModel.toDto(): CoverArtResponseDto{
    return CoverArtResponseDto(
        result = null,
        response = null,
        data = data?.toDto(),

        limit = limit,
        offset = offset,
        total = total
    )
}
