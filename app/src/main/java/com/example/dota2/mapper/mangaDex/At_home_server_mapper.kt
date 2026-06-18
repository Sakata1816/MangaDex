package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.AtHomeServerDto
import com.example.dota2.data.remote.mangaDex.dto.ChapterServerDto
import com.example.dota2.domain.model.server.AtHomeServerModel
import com.example.dota2.domain.model.server.ChapterServerModel

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