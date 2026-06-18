package com.example.dota2.domain.useCase.manga

import android.icu.text.CaseMap
import com.example.dota2.domain.model.server.MangaListResponseModel
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.MangaFilters
import com.example.dota2.domain.state.toApiValue
import javax.inject.Inject

class SearchMangaUseCase @Inject constructor(
    private val repository: MangaRepository
) {

    private companion object {
        const val PAGE_SIZE = 50
    }

    suspend operator fun invoke(
        page:Int = 0,
        title: String? = null,
        filters: MangaFilters = MangaFilters()
    ): Result<MangaListResponseModel> {

        return repository.getMangaList(
            limit = PAGE_SIZE,
            offset = PAGE_SIZE * page,
            title = title,
            authorOrArtist = filters.authorOrArtist,
            authors = filters.author,
            artists = filters.artist,
            year = filters.year,
            includedTags = filters.includedTags,
            includedTagsMode = "AND",
            excludedTags = filters.excludedTags,
            excludedTagsMode = "OR",
            status = filters.status?.map { it.toApiValue() },
            originalLanguage = null,
            excludedOriginalLanguage = null,
            availableTranslatedLanguage = null,
            publicationDemographic = filters.publicationDemographic?.map { it.toApiValue() },
            ids = null,
            contentRating = filters.contentRating?.map { it.toApiValue() },
            createdAtSince = null,
            updatedAtSince = null,
            orderLatestUploadedChapter = filters.orderLatestUploadedChapter?.toApiValue(),
            includes = filters.includes?.map { it.toApiValue() },
            hasAvailableChapters = filters.availableChapter.toApiValue(),
            hasUnavailableChapters = null,
            orderFollowedCount = filters.orderFollowedCount?.toApiValue(),
            orderRating = filters.orderRating?.toApiValue()
        )

    }
}