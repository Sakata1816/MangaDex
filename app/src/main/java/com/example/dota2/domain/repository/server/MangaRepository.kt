package com.example.dota2.domain.repository.server

import com.example.dota2.domain.model.server.AuthorListResponceModel
import com.example.dota2.domain.model.server.AuthorResponceModel
import com.example.dota2.domain.model.server.ChapterListResponseModel
import com.example.dota2.domain.model.server.ChapterResponseModel
import com.example.dota2.domain.model.server.CoverArtListResponseModel
import com.example.dota2.domain.model.server.CoverArtResponseModel
import com.example.dota2.domain.model.server.MangaAggregateResponseModel
import com.example.dota2.domain.model.server.MangaFeedResponceModel
import com.example.dota2.domain.model.server.MangaListResponseModel
import com.example.dota2.domain.model.server.MangaRecommendationListResponseModel
import com.example.dota2.domain.model.server.MangaRelationListResponseModel
import com.example.dota2.domain.model.server.MangaResponseModel
import com.example.dota2.domain.model.server.ScanlationGroupListResponseModel
import com.example.dota2.domain.model.server.ScanlationGroupResponseModel
import com.example.dota2.domain.model.server.TagListResponseModel



interface MangaRepository {

    suspend fun getAuthors(
        limit: Int?,
        offset: Int?,
        name: String?,
        order: String?,
        includes: List<String>?,
    ): Result<AuthorListResponceModel>

    suspend fun getAuthorById(
        id: String,
        includes: List<String>?
        ): Result<AuthorResponceModel>


    suspend fun getChapters(
        limit: Int,
        offset: Int?,
        ids: List<String>?,
        title: String?,
        groups: List<String>?,
        excludedGroups: List<String>?,
        uploader: String?,
        mangaUid: String?,
        volume: String?,
        chapter: String?,
        translatedLanguage: List<String>?,
        originalLanguage: List<String>?,
        excludedOriginalLanguage: List<String>?,
        contentRating: List<String>?,
        excludedUploaders: List<String>?,
        includeFutureUpdates: Int?,
        includeEmptyPages: Int?,
        includeFuturePublishAt: Int?,
        includeExternalUrl: Int?,
        includeUnavailable: Int?,
        orderCreatedAt: String?,
        orderUpdatedAt: String?,
        orderPublishAt: String?,
        orderReadableAt: String?,
        orderVolume: String?,
        orderChapter: String?,
        includes: List<String>?
    ): Result<ChapterListResponseModel>


    suspend fun getChapterById(
        id: String,
        includes: List<String>?
    ): Result<ChapterResponseModel>


    suspend fun getCovers(
        limit: Int?,
        offset: Int?,
        mangaUid: List<String>?,
        ids: List<String>?,
        locales: List<String>?,
        orderCreatedAt: String?,
        orderUpdatedAt: String?,
        orderVolume: String?,
        includes: List<String>?
    ): Result<CoverArtListResponseModel>


    suspend fun getCoverById(id: String,
                              includes: List<String>?
    ): Result<CoverArtResponseModel>


    suspend fun getMangaList(
        limit: Int?,
        offset: Int?,
        title: String?,
        authorOrArtist: String?,
        authors: List<String>?,
        artists: List<String>?,
        includedTags: List<String>?,
        includedTagsMode: String?,
        excludedTags: List<String>?,
        excludedTagsMode: String?,
        status: List<String>?,
        originalLanguage: List<String>?,
        excludedOriginalLanguage: List<String>?,
        availableTranslatedLanguage: List<String>?,
        publicationDemographic: List<String>?,
        ids: List<String>?,
        contentRating: List<String>?,
        createdAtSince: String?,
        updatedAtSince: String?,
        orderLatestUploadedChapter: String?,
        includes: List<String>?,
        hasAvailableChapters: String?,
        hasUnavailableChapters: String?
    ): Result<MangaListResponseModel>


    suspend fun getMangaById(id: String,
                             includes: List<String>?
    ): Result<MangaResponseModel>


    suspend fun getMangaAggregate(
        mangaId: String,
        translatedLanguage: List<String>?,
        groups: List<String>?,
        includeUnavailable: Int?
    ): Result<MangaAggregateResponseModel>


    suspend fun getMangaRecommendations(
        mangaId: String,
        includes: List<String>?,
        orderScore: String?,
        contentRating: List<String>?
    ): Result<MangaRecommendationListResponseModel>


    suspend fun getMangaFeed(
        mangaId: String,
        limit: Int?,
        offset: Int?,
        translatedLanguage: List<String>?,
        originalLanguage: List<String>?,
        excludedOriginalLanguage: List<String>?,
        contentRating: List<String>?,
        excludedGroups: List<String>?,
        excludedUploaders: List<String>?,
        includeFutureUpdates: Int?,
        orderCreatedAt: String?,
        orderUpdatedAt: String?,
        orderPublishAt: String?,
        orderReadableAt: String?,
        orderVolume: String?,
        orderChapter: String?,
        includes: List<String>?,
        includeEmptyPages: Int?,
        includeFuturePublishAt: Int?,
        includeExternalUrl: Int?,
        includeUnavailable: Int?
    ): Result<MangaFeedResponceModel>


    suspend fun getMangaRelations(
        mangaId: String,
        includes: List<String>?
    ): Result<MangaRelationListResponseModel>

    suspend fun mangaTeg(): Result<TagListResponseModel>


    suspend fun getGroups(
        limit: Int?,
        offset: Int?,
        ids: List<String>?,
        name: String?,
        focusedLanguage: String?,
        includes: List<String>?,
        orderLatestUploadedChapter: String?
        ): Result<ScanlationGroupListResponseModel>


    suspend fun getGroupsById(
        id: String,
        includes: List<String>?
    ): Result<ScanlationGroupResponseModel>


}