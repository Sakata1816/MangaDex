package com.example.dota2.mapper.mangaDex

import com.example.dota2.data.remote.mangaDex.dto.AuthorAttributesDto
import com.example.dota2.data.remote.mangaDex.dto.AuthorItemDto
import com.example.dota2.data.remote.mangaDex.dto.AuthorListResponceDto
import com.example.dota2.data.remote.mangaDex.dto.RelationshipDto
import com.example.dota2.domain.model.server.AuthorAttributesModel
import com.example.dota2.domain.model.server.AuthorItemModel
import com.example.dota2.domain.model.server.AuthorListResponceModel
import com.example.dota2.domain.model.server.RelationshipModel



fun AuthorListResponceDto.toModel(): AuthorListResponceModel {
    return AuthorListResponceModel(
        data = data?.map { it.toModel() },

        limit = limit,
        offset = offset,
        total = total
    )
}


fun AuthorItemDto.toModel(): AuthorItemModel{
    return AuthorItemModel(
        id = id,
        type = type,
        attributes = attributes?.toModel(),
        relationships = relationships?.map { it.toModel() }
    )
}


fun AuthorAttributesDto.toModel():AuthorAttributesModel{
    return AuthorAttributesModel(
        name = name,
        imageUrl = imageUrl,
        biography = biography,

        twitter = twitter,
        pixiv = pixiv,
        melonBook = melonBook,
        fanBox = fanBox,
        booth = booth,
        nicoVideo = nicoVideo,
        skeb = skeb,
        fantia = fantia,
        tumblr = tumblr,
        youtube = youtube,
        weibo = weibo,
        naver = naver,
        namicomi = namicomi,
        website = website,

        version = version,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}


fun RelationshipDto.toModel(): RelationshipModel{
    return RelationshipModel(
        id = id,
        type = type,
        related = related,
        attributes = attributes
    )
}





fun AuthorListResponceModel.toDto(): AuthorListResponceDto{
    return AuthorListResponceDto(
        result = null,
        response = null,
        data = data?.map { it.toDto() },

        limit = limit,
        offset = offset,
        total = total
    )
}


fun AuthorItemModel.toDto(): AuthorItemDto{
    return AuthorItemDto(
        id = id,
        type = type,
        attributes = attributes?.toDto(),
        relationships = relationships?.map { it.toDto() }
    )
}


fun AuthorAttributesModel.toDto(): AuthorAttributesDto{
    return AuthorAttributesDto(
        name = name,
        imageUrl = imageUrl,
        biography = biography,

        twitter = twitter,
        pixiv = pixiv,
        melonBook = melonBook,
        fanBox = fanBox,
        booth = booth,
        nicoVideo = nicoVideo,
        skeb = skeb,
        fantia = fantia,
        tumblr = tumblr,
        youtube = youtube,
        weibo = weibo,
        naver = naver,
        namicomi = namicomi,
        website = website,

        version = version,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}


fun RelationshipModel.toDto(): RelationshipDto{
    return RelationshipDto(
        id = id,
        type = type,
        related = related,
        attributes = attributes
    )
}


