package com.example.dota2.domain.model.server

data class AuthorListResponceModel(
    val result: String?,
    val response: String?,
    val data: List<AuthorItemModel>?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)

data class AuthorItemModel(
    val id: String?,
    val type: String?,
    val attributes: AuthorAttributesModel?,
    val relationships: List<RelationshipModel>?
)

data class AuthorAttributesModel(
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

data class RelationshipModel(
    val id: String?,
    val type: String?,
    val related: String?,
    val attributes: Map<String, Any?>?
)
