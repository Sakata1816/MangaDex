package com.example.MangaDex.mapper.mangaDex

import com.example.MangaDex.data.remote.mangaDex.dto.MangaResponseDto
import com.example.MangaDex.domain.model.server.MangaResponseModel



fun MangaResponseDto.toModel(): MangaResponseModel{
    return MangaResponseModel(
        data = data?.toModel(),

        limit = limit,
        offset = offset,
        total = total
    )
}



fun MangaResponseModel.toDto(): MangaResponseDto{
    return MangaResponseDto(
        result = null,
        response = null,
        data = data?.toDto(),

        limit = limit,
        offset = offset,
        total = total
    )
}