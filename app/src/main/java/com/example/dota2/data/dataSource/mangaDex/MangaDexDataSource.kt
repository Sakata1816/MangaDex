package com.example.dota2.data.dataSource.mangaDex

import com.example.dota2.data.remote.mangaDex.api.MangaDexApi
import javax.inject.Inject

class MangaDexDataSource @Inject constructor(
    private val api: MangaDexApi
){

    suspend fun getAuthors(limit: Int? ,
                           offset: Int?,
                           name: String?,
                           order: String?,
                           includes: List<String>?
    ) = api.getAuthors(limit, offset, name, order, includes)


    suspend fun getAuthorById(id: String,
                              includes: List<String>?
    ) = api.getAuthorById(id,includes)


    suspend fun getChapters(limit: Int,
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
    ) = api.getChapters(limit,offset,ids,title,groups,excludedGroups,uploader,mangaUid,volume,chapter,translatedLanguage,originalLanguage,excludedOriginalLanguage,contentRating,excludedUploaders,includeFutureUpdates,includeEmptyPages,includeFuturePublishAt,includeExternalUrl,includeUnavailable,orderCreatedAt,orderUpdatedAt,orderPublishAt,orderReadableAt,orderVolume,orderChapter,includes)

    suspend fun getChapterById(id: String,
                               includes: List<String>?
    ) = api.getChapterById(id,includes)

    suspend fun getCovers(limit: Int?,
                          offset: Int?,
                          mangaUid: List<String>?,
                          ids: List<String>?,
                          locales: List<String>?,
                          orderCreatedAt: String?,
                          orderUpdatedAt: String?,
                          orderVolume: String?,
                          includes: List<String>?
    ) = api.getCovers(limit, offset, mangaUid, ids, locales, orderCreatedAt, orderUpdatedAt, orderVolume, includes)


    suspend fun getCoverById(id: String,
                             includes: List<String>?) = api.getCoverById(id,includes)


    suspend fun getMangaList(limit : Int?,
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
                             orderFollowedCount: String?,
                             orderRating: String?,
                             includes: List<String>?,
                             hasAvailableChapters: String?,
                             hasUnavailableChapters: String?
    ) = api.getMangaList(limit,offset,title,authorOrArtist,authors,artists,includedTags,includedTagsMode,excludedTags,excludedTagsMode,status,originalLanguage,excludedOriginalLanguage,availableTranslatedLanguage,publicationDemographic,ids,contentRating,createdAtSince,updatedAtSince,orderLatestUploadedChapter,orderFollowedCount,orderRating,includes,hasAvailableChapters,hasUnavailableChapters)


    suspend fun getMangaById(id: String,
                             includes: List<String>?
    ) = api.getMangaById(id,includes)


    suspend fun getMangaAggregate(mangaId: String,
                                  translatedLanguage: List<String>?,
                                  groups: List<String>?,
                                  includeUnavailable: Int?
    ) = api.getMangaAggregate(mangaId,translatedLanguage,groups,includeUnavailable)


    suspend fun getMangaRecommendations(mangaId: String,
                                        includes: List<String>?,
                                        orderScore: String?,
                                        contentRating: List<String>?
    ) = api.getMangaRecommendations(mangaId,includes,orderScore,contentRating)


    suspend fun getMangaFeed(mangaId: String,
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
    ) = api.getMangaFeed(mangaId,limit,offset,translatedLanguage,originalLanguage,excludedOriginalLanguage,contentRating,excludedGroups,excludedUploaders,includeFutureUpdates,orderCreatedAt,orderUpdatedAt,orderPublishAt,orderReadableAt,orderVolume,orderChapter,includes,includeEmptyPages,includeFuturePublishAt,includeExternalUrl,includeUnavailable)


    suspend fun getMangaRelations(mangaId: String,
                                  includes: List<String>?
    ) = api.getMangaRelations(mangaId,includes)


    suspend fun getMangaTeg() = api.mangaTeg()


    suspend fun getGroups(limit: Int?,
                          offset: Int?,
                          ids: List<String>?,
                          name: String?,
                          focusedLanguage: String?,
                          includes: List<String>?,
                          orderLatestUploadedChapter: String?
    ) = api.getGroups(limit,offset,ids,name,focusedLanguage,includes,orderLatestUploadedChapter)


    suspend fun getGroupById(id: String,
                             includes: List<String>?
    ) = api.getGroupById(id,includes)


}