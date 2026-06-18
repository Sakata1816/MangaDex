package com.example.dota2.domain.useCase.manga

import android.util.Log
import com.example.dota2.domain.model.server.MangaFeedResponceModel
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.ContentRating
import com.example.dota2.domain.state.IncludeType
import com.example.dota2.domain.state.MangaVolumeChaptersState
import com.example.dota2.domain.state.SortOrder
import com.example.dota2.domain.state.Status
import com.example.dota2.domain.state.TranslatedLanguage
import com.example.dota2.domain.state.toApiValue
import org.intellij.lang.annotations.Language
import javax.inject.Inject
import kotlin.Result

class GetMangaChaptersUseCase @Inject constructor(
    private val repository: MangaRepository
) {

    private companion object {
        const val PAGE_SIZE = 96
    }


    suspend operator fun invoke(
        mangaId: String,
        page: Int = 0,
        translatedLanguage: List<TranslatedLanguage>? = null,
        includes: List<IncludeType> = listOf(IncludeType.MANGA),
        rating: List<ContentRating>? = null,
    ): Result<List<MangaVolumeChaptersState>?>{


        return repository.getMangaFeed(
            mangaId = mangaId,
            limit = PAGE_SIZE,
            offset = PAGE_SIZE * page,
            translatedLanguage = translatedLanguage?.map { it.toApiValue() },
            originalLanguage = null,
            excludedOriginalLanguage = null,
            contentRating = rating?.map { it.toApiValue() },
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
            includeEmptyPages = null,
            includeFuturePublishAt = null,
            includeExternalUrl = null,
            includeUnavailable = "0"
        )
            .map { response->

                response.data
                    ?.groupBy {
                         it.attributes?.chapter ?: "Unknown"
                    }
                    ?.map { (chapterNum, translations) ->
                        MangaVolumeChaptersState(
                            chapter = chapterNum,
                            title = translations.firstOrNull()?.attributes?.title,
                            translations = translations
                        )
                    }
                    ?.sortedByDescending { it.chapter?.toFloatOrNull() }
            }
    }
}