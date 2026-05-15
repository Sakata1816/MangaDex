package com.example.dota2.domain.model.server


data class ScanlationGroupResponseModel(
    val result: String?,
    val response: String?,
    val data: ScanlationGroupModel?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)