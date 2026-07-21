package com.example.MangaDex.domain.model.server


data class ScanlationGroupResponseModel(
    val data: ScanlationGroupModel?,

    val limit: Int?,
    val offset: Int?,
    val total: Int?
)