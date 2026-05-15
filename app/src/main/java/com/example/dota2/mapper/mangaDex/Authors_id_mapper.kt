package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.AuthorResponceDto
import com.example.dota2.domain.model.server.AuthorResponceModel


fun AuthorResponceDto.toModel():AuthorResponceModel {
    return AuthorResponceModel(
        result = result,
        response = response,
        data = data?.toModel(),

        limit = limit,
        offset = offset,
        total = total
    )

}



fun AuthorResponceModel.toDto(): AuthorResponceDto{
    return AuthorResponceDto(
        result = result,
        response = response,
        data = data?.toDto(),

        limit = limit,
        offset = offset,
        total = total
    )

}