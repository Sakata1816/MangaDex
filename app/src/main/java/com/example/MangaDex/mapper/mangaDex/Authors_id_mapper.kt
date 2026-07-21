package com.example.MangaDex.mapper.mangaDex

import com.example.MangaDex.data.remote.mangaDex.dto.AuthorResponceDto
import com.example.MangaDex.domain.model.server.AuthorResponceModel


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