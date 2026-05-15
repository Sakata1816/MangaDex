package com.example.dota2.data.remote.mangaDex.dto

data class ScanlationGroupResponseDto(
    val result: String?,
    val response: String?,
    val data: ScanlationGroupDto?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)