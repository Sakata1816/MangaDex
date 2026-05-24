package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.MangaResponseDto
import com.example.dota2.domain.model.server.MangaResponseModel



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