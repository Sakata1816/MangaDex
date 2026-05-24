package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.MangaDto
import com.example.dota2.data.remote.mangaDex.dto.MangaFeedResponceDto
import com.example.dota2.domain.model.server.MangaFeedResponceModel


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