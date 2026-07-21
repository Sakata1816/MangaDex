package com.example.MangaDex.mapper.mangaDex

import com.example.MangaDex.data.remote.mangaDex.dto.ApiErrorDto
import com.example.MangaDex.data.remote.mangaDex.dto.ErrorResponseDto
import com.example.MangaDex.domain.model.server.ApiErrorModel
import com.example.MangaDex.domain.model.server.ErrorResponseModel


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