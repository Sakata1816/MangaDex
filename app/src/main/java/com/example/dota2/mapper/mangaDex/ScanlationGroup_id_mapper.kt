package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.ScanlationGroupResponseDto
import com.example.dota2.domain.model.server.ScanlationGroupResponseModel


fun ScanlationGroupResponseDto.toModel(): ScanlationGroupResponseModel{
    return ScanlationGroupResponseModel(
        result = result,
        response = response,
        data = data?.toModel(),

        limit = limit,
        offset = offset,
        total = total
    )
}




fun ScanlationGroupResponseModel.toDto(): ScanlationGroupResponseDto{
    return ScanlationGroupResponseDto(
        result = result,
        response = response,
        data = data?.toDto(),

        limit = limit,
        offset = offset,
        total = total
    )
}