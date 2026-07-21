package com.example.MangaDex.mapper.mangaDex

import com.example.MangaDex.data.remote.mangaDex.dto.ChapterResponseDto
import com.example.MangaDex.domain.model.server.ChapterResponseModel


fun ChapterResponseDto.toModel(): ChapterResponseModel {
    return ChapterResponseModel(
        data = data?.toModel(),

        limit = limit,
        offset = offset,
        total = total
    )
}



fun ChapterResponseModel.toDto(): ChapterResponseDto{
    return ChapterResponseDto(
        result = null,
        response = null,
        data = data?.toDto(),

        limit = limit,
        offset = offset,
        total = total
    )
}
