package com.example.dota2.domain.useCase.manga

import com.example.dota2.domain.model.server.MangaRelationListResponseModel
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.IncludeType
import com.example.dota2.domain.state.toApiValue
import javax.inject.Inject

class GetMangaRelationsUseCase @Inject constructor(
    private val repository: MangaRepository
){
    suspend operator fun invoke(
        mangaId: String,
        includes: List<IncludeType> = listOf(IncludeType.COVER_ART)
    ): Result<MangaRelationListResponseModel>{
        return repository.getMangaRelations(
            mangaId = mangaId,
            includes = includes.map { it.toApiValue() }
        )
    }
}