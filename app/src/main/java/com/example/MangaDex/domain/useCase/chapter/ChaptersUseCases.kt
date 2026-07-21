package com.example.MangaDex.domain.useCase.chapter

import com.example.MangaDex.domain.repository.server.MangaRepository
import com.example.MangaDex.domain.state.AuthorContentType
import com.example.MangaDex.domain.state.toApiValue
import javax.inject.Inject

class ChaptersUseCases @Inject constructor(
    private val repository: MangaRepository
) {

    suspend operator fun invoke(
        mangaUid: String,
        sortDescending: Boolean = false,
        page: Int = 0,
        limit: Int = 20,
        contentType: List<AuthorContentType>?
    ){

        val offset = page * limit

        val order = if(sortDescending)"desc" else "asc"

        repository.getChapters(
            limit = limit,
            offset = offset,
            mangaUid = mangaUid,
            orderChapter = order,
            ids = null,
            title = null,
            groups = null,
            excludedGroups = null,
            uploader = null,
            volume = null,
            chapter = null,
            originalLanguage = null,
            excludedOriginalLanguage = null,
            contentRating = null,
            excludedUploaders = null,
            includeFutureUpdates = 0,
            includeEmptyPages = 0,
            includeFuturePublishAt = 0,
            includeExternalUrl = 0,
            includeUnavailable = 0,
            orderCreatedAt = null,
            orderUpdatedAt = null,
            orderPublishAt = null,
            orderReadableAt = null,
            orderVolume = null,
            includes = contentType?.map {it.toApiValue()},
            translatedLanguage = null
        )
    }

    }