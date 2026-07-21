package com.example.MangaDex.domain.useCase.cover

import com.example.MangaDex.domain.model.server.CoverArtListResponseModel
import com.example.MangaDex.domain.repository.server.MangaRepository
import javax.inject.Inject

class CoversUseCase @Inject constructor(
    private val repository: MangaRepository
) {
    suspend operator fun invoke(
        mangaUid: List<String>,
    ): Result<CoverArtListResponseModel>{
        return repository.getCovers(
            limit = mangaUid.size.coerceAtMost(100),
            offset = 0,
            mangaUid = mangaUid,
            ids = null,
            locales = null,
            orderCreatedAt = null,
            orderUpdatedAt = null,
            orderVolume = null,
            includes = null
        )
    }
}