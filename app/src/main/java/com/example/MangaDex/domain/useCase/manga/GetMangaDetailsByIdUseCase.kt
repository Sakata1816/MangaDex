package com.example.MangaDex.domain.useCase.manga

import com.example.MangaDex.domain.model.server.MangaResponseModel
import com.example.MangaDex.domain.repository.server.MangaRepository
import com.example.MangaDex.domain.state.IncludeType
import com.example.MangaDex.domain.state.toApiValue
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
