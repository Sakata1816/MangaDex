package com.example.MangaDex.mapper.mangaDex

import com.example.MangaDex.data.remote.mangaDex.dto.MangaFeedResponceDto
import com.example.MangaDex.domain.model.server.MangaFeedResponceModel


fun MangaFeedResponceDto.toModel(): MangaFeedResponceModel{
    return MangaFeedResponceModel(
        data = data?.map { it.toModel() },

        limit = limit,
        offset = offset,
        total = total
    )
}




fun MangaFeedResponceModel.toDto(): MangaFeedResponceDto{
    return MangaFeedResponceDto(
        result = null,
        response = null,
        data = data?.map { it.toDto() },

        limit = limit,
        offset = offset,
        total = total
    )
}