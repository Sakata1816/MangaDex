package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.ChapterAttributesDto
import com.example.dota2.data.remote.mangaDex.dto.ChapterDto
import com.example.dota2.data.remote.mangaDex.dto.ChapterListResponseDto
import com.example.dota2.domain.model.server.ChapterAttributesModel
import com.example.dota2.domain.model.server.ChapterListResponseModel
import com.example.dota2.domain.model.server.ChapterModel


    fun ChapterListResponseDto.toModel(): ChapterListResponseModel {
        return ChapterListResponseModel(
            result = result,
            response = response,
            data = data?.map { it.toModel() },

            limit = limit,
            offset = offset,
            total = total
        )
    }


    fun ChapterDto.toModel():ChapterModel{
        return ChapterModel(
            id = id,
            type = type,
            attributes = attributes?.toModel(),
            relationships = relationships?.map { it.toModel() }
        )
    }


    fun ChapterAttributesDto.toModel(): ChapterAttributesModel {
        return ChapterAttributesModel(
            title = title,
            volume = volume,
            chapter = chapter,
            pages = pages,
            translatedLanguage = translatedLanguage,
            uploader = uploader,
            externalUrl = externalUrl,
            version = version,
            createdAt = createdAt,
            updatedAt = updatedAt,
            publishAt = publishAt,
            readableAt = readableAt,
            isUnavailable = isUnavailable
        )
    }




    fun ChapterListResponseModel.toDto(): ChapterListResponseDto {
        return ChapterListResponseDto(
            result = result,
            response = response,
            data = data?.map { it.toDto() },

            limit = limit,
            offset = offset,
            total = total
        )
    }


    fun ChapterModel.toDto(): ChapterDto{
        return ChapterDto(
            id = id,
            type = type,
            attributes = attributes?.toDto(),
            relationships = relationships?.map { it.toDto() }
        )
    }


    fun ChapterAttributesModel.toDto(): ChapterAttributesDto{
        return ChapterAttributesDto(
            title = title,
            volume = volume,
            chapter = chapter,
            pages = pages,
            translatedLanguage = translatedLanguage,
            uploader = uploader,
            externalUrl = externalUrl,
            version = version,
            createdAt = createdAt,
            updatedAt = updatedAt,
            publishAt = publishAt,
            readableAt = readableAt,
            isUnavailable = isUnavailable
        )
}
