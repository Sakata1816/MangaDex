package com.example.dota2.domain.useCase.manga

import android.icu.text.CaseMap
import com.example.dota2.domain.model.server.MangaListResponseModel
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.MangaFilters
import com.example.dota2.domain.state.toString
import javax.inject.Inject

class SearchMangaUseCase @Inject constructor(
    private val repository: MangaRepository
) {

    suspend operator fun invoke(
        page:Int = 0,
        title: String? = null,
        authorOrArtist: String? = null,
        filters: MangaFilters = MangaFilters()
    ): Result<MangaListResponseModel> {
        return repository.getMangaList(
            limit = 20,
            offset = page * 20,
            title = title,
            authorOrArtist = authorOrArtist,
            authors = null,
            artists = null,
            includedTags = filters.includedTags,
            includedTagsMode = "OR",
            excludedTags = filters.excludedTags,
            excludedTagsMode = "OR",
            status = filters.status?.map { it.toString() },
            originalLanguage = null,
            excludedOriginalLanguage = null,
            availableTranslatedLanguage = null,
            publicationDemographic = filters.publicationDemographic?.map { it.toString() },
            ids = null,
            contentRating = filters.contentRating?.map { it.toString() },
            createdAtSince = null,
            updatedAtSince = null,
            orderLatestUploadedChapter = filters.orderLatestUploadedChapter.toString(),
            includes = filters.includes?.map { it.toString() },
            hasAvailableChapters = null,
            hasUnavailableChapters = null
        )

    }
}