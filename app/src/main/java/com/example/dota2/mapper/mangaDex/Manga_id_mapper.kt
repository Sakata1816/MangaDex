package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.MangaResponseDto
import com.example.dota2.domain.model.server.MangaResponseModel


fun MangaResponseDto.toModel(): MangaResponseModel{
    return MangaResponseModel(
        result = result,
        response = response,
        data = data?.toModel(),

        limit = limit,
        offset = offset,
        total = total
    )
}



fun MangaResponseModel.toDto(): MangaResponseDto{
    return MangaResponseDto(
        result = result,
        response = response,
        data = data?.toDto(),

        limit = limit,
        offset = offset,
        total = total
    )
}