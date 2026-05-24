package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.TagListResponseDto
import com.example.dota2.domain.model.server.TagListResponseModel


fun TagListResponseDto.toModel(): TagListResponseModel{
    return TagListResponseModel(
        data = data?.map { it.toModel() },

        limit = limit,
        offset = offset,
        total = total
    )
}




fun TagListResponseModel.toDto(): TagListResponseDto{
    return TagListResponseDto(
        result = null,
        response = null,
        data = data?.map { it.toDto() },

        limit = limit,
        offset = offset,
        total = total
    )
}