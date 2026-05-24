package com.example.dota2.domain.useCase.manga

import com.example.dota2.domain.model.server.MangaListResponseModel
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.IncludeType
import com.example.dota2.domain.state.MangaFilters
import com.example.dota2.domain.state.SortOrder
import com.example.dota2.domain.state.toApiValue
import javax.inject.Inject

class GetMangaListUseCase @Inject constructor(
    private val repository: MangaRepository
) {

    private companion object {
        const val PAGE_SIZE = 20
    }

    suspend operator fun invoke(
        page: Int = 0,
        filters: MangaFilters = MangaFilters()
        ): Result<MangaListResponseModel> {

        val chapter = if(filters.availableChapter){"true"} else {"false"}

        return repository.getMangaList(
            limit = PAGE_SIZE,
            offset = page * PAGE_SIZE,
            hasAvailableChapters = chapter,
            orderLatestUploadedChapter = "desc",
            hasUnavailableChapters = null,
            includes = filters.includes?.map { it.toApiValue() },
            title = null,
            authorOrArtist = null,
            authors = null,
            artists = null,
            includedTags = null,
            includedTagsMode = null,
            excludedTags = null,
            excludedTagsMode = null,
            status = null,
            originalLanguage = null,
            excludedOriginalLanguage = null,
            availableTranslatedLanguage = null,
            publicationDemographic = null,
            ids = null,
            contentRating = null,
            createdAtSince = null,
            updatedAtSince = null,

            orderFollowedCount = filters.orderFollowedCount?.toApiValue(),
            orderRating = filters.orderRating?.toApiValue()
        )
    }
}