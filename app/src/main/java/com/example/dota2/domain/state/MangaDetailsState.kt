package com.example.dota2.domain.state

import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.model.server.MangaRelationModel

data class MangaDetailsState(
    val details: MangaModel? = null,
    val relations : List<MangaRelationModel> = emptyList()
)
