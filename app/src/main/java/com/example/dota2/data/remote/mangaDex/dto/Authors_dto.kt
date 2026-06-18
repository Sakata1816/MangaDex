package com.example.dota2.data.remote.mangaDex.dto

data class AuthorListResponceDto(
    val result: String?,
    val response: String?,
    val data: List<AuthorItemDto>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class AuthorItemDto(
    val id: String,
    val type: String?,
    val attributes: AuthorAttributesDto?,
    val relationships: List<RelationshipDto>?
)

data class AuthorAttributesDto(
    val name: String?,
    val imageUrl: String?,
    val biography: Map<String, String>?,

    val twitter: String?,
    val pixiv: String?,
    val melonBook: String?,
    val fanBox: String?,
    val booth: String?,
    val nicoVideo: String?,
    val skeb: String?,
    val fantia: String?,
    val tumblr: String?,
    val youtube: String?,
    val weibo: String?,
    val naver: String?,
    val namicomi: String?,
    val website: String?,

    val version: Int?,
    val createdAt: String?,
    val updatedAt: String?
)

data class RelationshipDto(
    val id: String?,
    val type: String?,
    val related: String?,
    val attributes: Map<String, Any?>?
)
