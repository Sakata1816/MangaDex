package com.example.dota2.domain.useCase.manga

import com.example.dota2.domain.model.server.MangaListResponseModel
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.MangaIncludeType
import javax.inject.Inject

class GetMangaListUseCase @Inject constructor(
    private val repository: MangaRepository
) {
    suspend operator fun invoke(
        page: Int = 0,
        availableChapter: Boolean = true
    ): Result<MangaListResponseModel> {

        val chapter = if(availableChapter){"true"} else {"false"}

        return repository.getMangaList(
            limit = 20,
            offset = page * 20,
            hasAvailableChapters = chapter,
            orderLatestUploadedChapter = "desc",
            hasUnavailableChapters = null,
            includes = null,
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
            updatedAtSince = null
        )
    }
}