package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.AuthorResponceDto
import com.example.dota2.domain.model.server.AuthorResponceModel


fun AuthorResponceDto.toModel():AuthorResponceModel {
    return AuthorResponceModel(
        data = data?.toModel(),

        limit = limit,
        offset = offset,
        total = total
    )

}



fun AuthorResponceModel.toDto(): AuthorResponceDto{
    return AuthorResponceDto(
        result = null,
        response = null,
        data = data?.toDto(),

        limit = limit,
        offset = offset,
        total = total
    )

}