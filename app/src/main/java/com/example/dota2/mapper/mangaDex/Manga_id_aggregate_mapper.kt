package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.ChapterAggregateDto
import com.example.dota2.data.remote.mangaDex.dto.MangaAggregateResponseDto
import com.example.dota2.data.remote.mangaDex.dto.VolumeDto
import com.example.dota2.domain.model.server.ChapterAggregateModel
import com.example.dota2.domain.model.server.MangaAggregateResponseModel
import com.example.dota2.domain.model.server.VolumeModel



fun MangaAggregateResponseDto.toModel(): MangaAggregateResponseModel {
    return MangaAggregateResponseModel(
        result = result,
        volumes = volumes?.mapValues { it.value.toModel() }
    )
}


fun VolumeDto.toModel(): VolumeModel {
    return VolumeModel(
        volume = volume,
        count = count,
        chapters = chapters?.mapValues { it.value.toModel() }
    )
}


fun ChapterAggregateDto.toModel(): ChapterAggregateModel {
    return ChapterAggregateModel(
        chapter = chapter,
        id = id,
        isUnavailable = isUnavailable,
        others = others,
        count = count
    )
}




fun MangaAggregateResponseModel.toDto(): MangaAggregateResponseDto {
    return MangaAggregateResponseDto(
        result = result,
        volumes = volumes?.mapValues { it.value.toDto() }
    )
}


fun VolumeModel.toDto(): VolumeDto {
    return VolumeDto(
        volume = volume,
        count = count,
        chapters = chapters?.mapValues { it.value.toDto() }
    )
}


fun ChapterAggregateModel.toDto(): ChapterAggregateDto {
    return ChapterAggregateDto(
        chapter = chapter,
        id = id,
        isUnavailable = isUnavailable,
        others = others,
        count = count
    )
}





