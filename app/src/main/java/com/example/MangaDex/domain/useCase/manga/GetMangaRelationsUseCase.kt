package com.example.MangaDex.domain.useCase.manga

import com.example.MangaDex.domain.model.server.MangaListResponseModel
import com.example.MangaDex.domain.repository.server.MangaRepository
import com.example.MangaDex.domain.state.IncludeType
import com.example.MangaDex.domain.state.toApiValue
import javax.inject.Inject

class GetMangaRelationsUseCase @Inject constructor(
    private val repository: MangaRepository,
    private val mangaList:GetMangaListUseCase
){
    suspend operator fun invoke(
        mangaId: String,
        page: Int = 0,
        includes: List<IncludeType> = listOf(IncludeType.MANGA)
    ): Result<MangaListResponseModel>{

        val  relationsResult =  repository.getMangaRelations(
            mangaId = mangaId,
            includes = includes.map { it.toApiValue() }
        )

        val response = relationsResult.getOrElse { return Result.failure(it) }

        val mangaIds = response.data?.flatMap { mangaRelationModel ->
            mangaRelationModel
                .relationships
                ?.filter { it.type == "manga" }
                ?.mapNotNull { it.id }
                ?: emptyList()
        }
            ?:emptyList()
        return mangaList(
            page = page,
           ids = mangaIds)
        }
}