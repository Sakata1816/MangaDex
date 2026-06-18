package com.example.dota2.domain.useCase.manga

import com.example.dota2.domain.model.server.MangaResponseModel
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.IncludeType
import com.example.dota2.domain.state.toApiValue
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetMangaDetailsByIdUseCase @Inject constructor(
    private val repository: MangaRepository
){

    suspend operator fun invoke(
        id: String,
        includes: List<IncludeType> = listOf(IncludeType.COVER_ART)
    ): Result<MangaResponseModel> {
        return repository.getMangaById(
            id = id,
            includes = includes.map { it.toApiValue() }
        )
    }
}
