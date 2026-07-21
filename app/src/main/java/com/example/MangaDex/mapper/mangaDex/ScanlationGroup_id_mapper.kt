package com.example.MangaDex.mapper.mangaDex

import com.example.MangaDex.data.remote.mangaDex.dto.ScanlationGroupResponseDto
import com.example.MangaDex.domain.model.server.ScanlationGroupResponseModel


fun ScanlationGroupResponseDto.toModel(): ScanlationGroupResponseModel{
    return ScanlationGroupResponseModel(
        data = data?.toModel(),

        limit = limit,
        offset = offset,
        total = total
    )
}




fun ScanlationGroupResponseModel.toDto(): ScanlationGroupResponseDto{
    return ScanlationGroupResponseDto(
        result = null,
        response = null,
        data = data?.toDto(),

        limit = limit,
        offset = offset,
        total = total
    )
}