package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.ScanlationGroupResponseDto
import com.example.dota2.domain.model.server.ScanlationGroupResponseModel


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