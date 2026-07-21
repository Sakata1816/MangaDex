package com.example.MangaDex.mapper.mangaDex

import com.example.MangaDex.data.remote.mangaDex.dto.AtHomeServerDto
import com.example.MangaDex.data.remote.mangaDex.dto.ChapterServerDto
import com.example.MangaDex.domain.model.server.AtHomeServerModel
import com.example.MangaDex.domain.model.server.ChapterServerModel

fun AtHomeServerDto.toModel(): AtHomeServerModel{
    return AtHomeServerModel(
        result = result,
        baseUrl = baseUrl,
        chapter = chapter?.toModel()
    )
}

fun ChapterServerDto.toModel(): ChapterServerModel {
    return ChapterServerModel(
        hash = hash,
        data = data,
        dataSaver = dataSaver
    )
}