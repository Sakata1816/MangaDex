package com.example.dota2.data.repository.server

import com.example.dota2.data.dataSource.mangaDex.MangaDexDataSource

import com.example.dota2.domain.model.server.*
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.mapper.mangaDex.toModel
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val server: MangaDexDataSource
): MangaRepository {


    override suspend fun getChapterServer(chapterId: String): Result<AtHomeServerModel> {
        return server.getChapterServer(chapterId).toResult {
            it.toModel()
        }
    }

    override suspend fun getAuthors(
        limit: Int?,
        offset: Int?,
        name: String?,
        order: String?,
        includes: List<String>?
    ): Result<AuthorListResponceModel> {
        return server.getAuthors(
            limit,
            offset,
            name,
            order,
            includes
        ).toResult {
            it.toModel()
        }

    }


    override suspend fun getAuthorById(
        id: String,
        includes: List<String>?
    ): Result<AuthorResponceModel> {
        return server.getAuthorById(
            id,
            includes
        ).toResult {
            it.toModel()
        }
    }


    override suspend fun getChapters(
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
    ): Result<ChapterListResponseModel> {
        return server.getChapters(
            limit,
            offset,
            ids,
            title,
            groups,
            excludedGroups,
            uploader,
            mangaUid,
            volume,
            chapter,
            translatedLanguage,
            originalLanguage,
            excludedOriginalLanguage,
            contentRating,
            excludedUploaders,
            includeFutureUpdates,
            includeEmptyPages,
            includeFuturePublishAt,
            includeExternalUrl,
            includeUnavailable,
            orderCreatedAt,
            orderUpdatedAt,
            orderPublishAt,
            orderReadableAt,
            orderVolume,
            orderChapter,
            includes
        ).toResult {
            it.toModel()
        }
    }


    override suspend fun getChapterById(
        id: String,
        includes: List<String>?
    ): Result<ChapterResponseModel> {
        return server.getChapterById(
            id,
            includes
        ).toResult {
            it.toModel()
        }
    }


    override suspend fun getCovers(
        limit: Int?,
        offset: Int?,
        mangaUid: List<String>?,
        ids: List<String>?,
        locales: List<String>?,
        orderCreatedAt: String?,
        orderUpdatedAt: String?,
        orderVolume: String?,
        includes: List<String>?
    ): Result<CoverArtListResponseModel> {
        return server.getCovers(
            limit,
            offset,
            mangaUid,
            ids,
            locales,
            orderCreatedAt,
            orderUpdatedAt,
            orderVolume,
            includes
        ).toResult {
            it.toModel()
        }

    }


    override suspend fun getCoverById(
        id: String,
        includes: List<String>?
    ): Result<CoverArtResponseModel> {
        return server.getCoverById(
            id,
            includes
        ).toResult {
            it.toModel()
        }
    }


    override suspend fun getMangaList(
        limit: Int?,
        offset: Int?,
        title: String?,
        authorOrArtist: String?,
        authors: List<String>?,
        artists: List<String>?,
        year: Int?,
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

        orderFollowedCount: String?,
        orderRating: String?,

        includes: List<String>?,
        hasAvailableChapters: String?,
        hasUnavailableChapters: String?
    ): Result<MangaListResponseModel> {
        return server.getMangaList(
            limit,
            offset,
            title,
            authorOrArtist,
            authors,
            artists,
            year,
            includedTags,
            includedTagsMode,
            excludedTags,
            excludedTagsMode,
            status,
            originalLanguage,
            excludedOriginalLanguage,
            availableTranslatedLanguage,
            publicationDemographic,
            ids,
            contentRating,
            createdAtSince,
            updatedAtSince,
            orderLatestUploadedChapter,
            orderFollowedCount,
            orderRating,
            includes,
            hasAvailableChapters,
            hasUnavailableChapters
        ).toResult {
            it.toModel()
        }
    }


    override suspend fun getMangaById(
        id: String,
        includes: List<String>?
    ): Result<MangaResponseModel> {
        return server.getMangaById(
            id,
            includes
        ).toResult {
            it.toModel()
        }
    }


    override suspend fun getMangaAggregate(
        mangaId: String,
        translatedLanguage: List<String>?,
        groups: List<String>?,
        includeUnavailable: Int?
    ): Result<MangaAggregateResponseModel> {
        return server.getMangaAggregate(
            mangaId,
            translatedLanguage,
            groups,
            includeUnavailable
        ).toResult {
            it.toModel()
        }
    }


    override suspend fun getMangaRecommendations(
        mangaId: String,
        includes: List<String>?,
        orderScore: String?,
        contentRating: List<String>?
    ): Result<MangaRecommendationListResponseModel> {
        return server.getMangaRecommendations(
            mangaId,
            includes,
            orderScore,
            contentRating
        ).toResult {
            it.toModel()
        }
    }

    override suspend fun getMangaFeed(
        mangaId: String,
        limit: Int?,
        offset: Int?,
        translatedLanguage: List<String>?,
        originalLanguage: List<String>?,
        excludedOriginalLanguage: List<String>?,
        contentRating: List<String>?,
        excludedGroups: List<String>?,
        excludedUploaders: List<String>?,
        includeFutureUpdates: String?,
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
        includeUnavailable: String?
    ): Result<MangaFeedResponceModel> {
        return server.getMangaFeed(
            mangaId,
            limit,
            offset,
            translatedLanguage,
            originalLanguage,
            excludedOriginalLanguage,
            contentRating,
            excludedGroups,
            excludedUploaders,
            includeFutureUpdates,
            orderCreatedAt,
            orderUpdatedAt,
            orderPublishAt,
            orderReadableAt,
            orderVolume,
            orderChapter,
            includes,
            includeEmptyPages,
            includeFuturePublishAt,
            includeExternalUrl,
            includeUnavailable
        ).toResult{
            it.toModel()
        }
    }


    override suspend fun getMangaRelations(
        mangaId: String,
        includes: List<String>?
    ): Result<MangaRelationListResponseModel> {
        return server.getMangaRelations(
            mangaId,
            includes
        ).toResult {
            it.toModel()
        }
    }


    override suspend fun mangaTeg(): Result<TagListResponseModel> {
        return server.getMangaTeg().toResult {
            it.toModel()
        }
    }


    override suspend fun getGroups(
        limit: Int?,
        offset: Int?,
        ids: List<String>?,
        name: String?,
        focusedLanguage: String?,
        includes: List<String>?,
        orderLatestUploadedChapter: String?
    ): Result<ScanlationGroupListResponseModel> {
        return server.getGroups(
            limit,
            offset,
            ids,
            name,
            focusedLanguage,
            includes,
            orderLatestUploadedChapter
        ).toResult{
            it.toModel()
        }
    }


    override suspend fun getGroupsById(
        id: String,
        includes: List<String>?
    ): Result<ScanlationGroupResponseModel> {
        return server.getGroupById(
            id,
            includes
        ).toResult {
            it.toModel()
        }
    }
}
