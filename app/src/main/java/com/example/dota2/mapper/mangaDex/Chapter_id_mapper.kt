package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.ChapterDto
import com.example.dota2.data.remote.mangaDex.dto.ChapterResponseDto
import com.example.dota2.domain.model.server.ChapterResponseModel


fun ChapterResponseDto.toModel(): ChapterResponseModel {
    return ChapterResponseModel(
        result = result,
        response = response,
        data = data?.toModel(),

        limit = limit,
        offset = offset,
        total = total
    )
}



fun ChapterResponseModel.toDto(): ChapterResponseDto{
    return ChapterResponseDto(
        result = result,
        response = response,
        data = data?.toDto(),

        limit = limit,
        offset = offset,
        total = total
    )
}
