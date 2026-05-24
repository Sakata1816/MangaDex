package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.ApiErrorDto
import com.example.dota2.data.remote.mangaDex.dto.ErrorResponseDto
import com.example.dota2.domain.model.server.ApiErrorModel
import com.example.dota2.domain.model.server.ErrorResponseModel


fun ErrorResponseDto.toModel(): ErrorResponseModel{
    return ErrorResponseModel(
        errors = errors?.map { it.toModel() }
    )
}

fun ApiErrorDto.toModel(): ApiErrorModel{
    return ApiErrorModel(
        id = id,
        status = status,
        title = title,
        detail = detail,
        context = context
    )
}