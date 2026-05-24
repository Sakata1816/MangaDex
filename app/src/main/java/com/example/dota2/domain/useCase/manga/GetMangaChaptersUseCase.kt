package com.example.dota2.domain.useCase.manga

import com.example.dota2.domain.model.server.MangaFeedResponceModel
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.IncludeType
import com.example.dota2.domain.state.MangaVolumeChaptersState
import com.example.dota2.domain.state.SortOrder
import com.example.dota2.domain.state.Status
import com.example.dota2.domain.state.toApiValue
import javax.inject.Inject
import kotlin.Result

class GetMangaChaptersUseCase @Inject constructor(
    private val repository: MangaRepository
) {

    private companion object {
        const val PAGE_SIZE = 50
    }


    suspend operator fun invoke(
        mangaId: String,
        page: Int = 0,
        includes: List<IncludeType> = listOf(IncludeType.MANGA),
        rating: List<Status> = listOf(Status.SAFE)
    ): Result<List<MangaVolumeChaptersState>>{


        return repository.getMangaFeed(
            mangaId = mangaId,
            limit = PAGE_SIZE,
            offset = PAGE_SIZE * page,
            translatedLanguage = null,
            originalLanguage = null,
            excludedOriginalLanguage = null,
            contentRating = rating.map { it.toApiValue() },
            excludedGroups = null,
            excludedUploaders = null,
            includeFutureUpdates = "1",
            orderCreatedAt = null,
            orderUpdatedAt = null,
            orderPublishAt = null,
            orderReadableAt = null,
            orderVolume = SortOrder.DESC.toApiValue(),
            orderChapter = SortOrder.DESC.toApiValue(),
            includes = includes.map { it.toApiValue() },
            includeEmptyPages = 0,
            includeFuturePublishAt = 0,
            includeExternalUrl = 0,
            includeUnavailable = "0"
        )
            .map { response->

                response.data
                    ?.groupBy {
                        it.attributes?.volume ?: "Unknown"
                    }
                    ?.map { (volume, chapters) ->
                        MangaVolumeChaptersState(
                            volume = volume,
                            chapters = chapters
                        )
                    }
                    ?:emptyList()
            }
    }
}