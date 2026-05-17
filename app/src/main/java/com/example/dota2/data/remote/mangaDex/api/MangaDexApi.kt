package com.example.dota2.data.remote.mangaDex.api

import com.example.dota2.data.remote.mangaDex.dto.AuthorListResponceDto
import com.example.dota2.data.remote.mangaDex.dto.AuthorResponceDto
import com.example.dota2.data.remote.mangaDex.dto.ChapterListResponseDto
import com.example.dota2.data.remote.mangaDex.dto.ChapterResponseDto
import com.example.dota2.data.remote.mangaDex.dto.CoverArtListResponseDto
import com.example.dota2.data.remote.mangaDex.dto.CoverArtResponseDto
import com.example.dota2.data.remote.mangaDex.dto.MangaAggregateResponseDto
import com.example.dota2.data.remote.mangaDex.dto.MangaDto
import com.example.dota2.data.remote.mangaDex.dto.MangaFeedResponceDto
import com.example.dota2.data.remote.mangaDex.dto.MangaListResponseDto
import com.example.dota2.data.remote.mangaDex.dto.MangaRecommendationDto
import com.example.dota2.data.remote.mangaDex.dto.MangaRecommendationListResponseDto
import com.example.dota2.data.remote.mangaDex.dto.MangaRelationListResponseDto
import com.example.dota2.data.remote.mangaDex.dto.MangaResponseDto
import com.example.dota2.data.remote.mangaDex.dto.ScanlationGroupListResponseDto
import com.example.dota2.data.remote.mangaDex.dto.ScanlationGroupResponseDto
import com.example.dota2.data.remote.mangaDex.dto.TagListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MangaDexApi {

    @GET("authors")
    suspend fun getAuthors(
        @Query("limit")
        limit: Int? = 10,
        @Query("offset")
        offset: Int? = 0,
        @Query("name")
        name: String? = null,
        @Query("order[name]")
        order: String? = "asc",
        @Query("includes[]")
            includes: List<String>? = null,
    ): Response<AuthorListResponceDto>


    @GET("authors/{id}")
    suspend fun getAuthorById(
        @Path("id")
        id: String,
        @Query("includes[]")
        includes: List<String>? = null,
    ): Response<AuthorResponceDto>


    @GET("chapter")
    suspend fun getChapters(
        @Query("limit")
        limit: Int = 10,
        @Query("offset")
        offset: Int? = 0,
        @Query("ids[]")
        ids: List<String>? = null,
        @Query("title")
        title: String? = null,
        @Query("groups[]")
        groups: List<String>? = null,
        @Query("excludedGroups[]")
        excludedGroups: List<String>? = null,
        @Query("uploader")
        uploader: String? = null,
        @Query("manga")
        mangaUid: String? = null,
        @Query("volume[]")
        volume: String? = null,
        @Query("chapter")
        chapter: String?=null,
        @Query("translatedLanguage[]")
        translatedLanguage: List<String>? = null,
        @Query("originalLanguage[]")
        originalLanguage: List<String>? = null,
        @Query("excludedOriginalLanguage[]")
        excludedOriginalLanguage: List<String>? = null,
        @Query("contentRating[]")
        contentRating: List<String>? = null,
        @Query("excludedUploaders[]")
        excludedUploaders: List<String>? = null,
        @Query("includeFutureUpdates")
        includeFutureUpdates: Int? = null,
        @Query("includeEmptyPages")
        includeEmptyPages: Int? = null,
        @Query("includeFuturePublishAt")
        includeFuturePublishAt: Int? = null,
        @Query("includeExternalUrl")
        includeExternalUrl: Int? = null,
        @Query("includeUnavailable")
        includeUnavailable: Int? = null,
        @Query("order[createdAt]")
        orderCreatedAt: String? = "asc",
        @Query("order[updatedAt]")
        orderUpdatedAt: String? = "asc",
        @Query("order[publishAt]")
        orderPublishAt: String? = "asc",
        @Query("order[readableAt]")
        orderReadableAt: String? = "asc",
        @Query("order[volume]")
        orderVolume: String? = "asc",
        @Query("order[chapter]")
        orderChapter: String? = "asc",
        @Query("includes")
        includes: List<String>? = null
    ): Response<ChapterListResponseDto>


    @GET("chapter/{id}")
    suspend fun getChapterById(
        @Path("id")
        id: String,
        @Query("includes[]")
        includes: List<String>?= null
    ):Response<ChapterResponseDto>


    @GET("Cover")
    suspend fun getCovers(
        @Query("limit")
        limit: Int? = 10,
        @Query("offset")
        offset: Int? = 0,
        @Query("manga[]")
        mangaUid: List<String>? = null,
        @Query("ids[]")
        ids: List<String>? = null,
        @Query("locales[]")
        locales: List<String>?=null,
        @Query("order[createdAt]")
        orderCreatedAt: String? = "asc",
        @Query("order[updatedAt]")
        orderUpdatedAt: String?= "asc",
        @Query("order[volume]")
        orderVolume: String? = "asc",
        @Query("includes[]")
        includes: List<String>? = null
    ):  Response<CoverArtListResponseDto>


    @GET("cover/{id}")
    suspend fun getCoverById(
        @Path("id")
        id: String,
        @Query("includes[]")
        includes: List<String>? = null

    ): Response<CoverArtResponseDto>


    @GET("manga")
    suspend fun getMangaList(
        @Query("limit")
        limit: Int? = 10,
        @Query("offset")
        offset: Int? = 0,
        @Query("title")
        title: String? = null,
        @Query("authorOrArtist")
        authorOrArtist: String? = null,
        @Query("authors[]")
        authors: List<String>? = null,
        @Query("artists[]")
        artists: List<String>? = null,
        @Query("includedTags[]")
        includedTags: List<String>? = null,
        @Query("includedTagsMode")
        includedTagsMode: String? = null,
        @Query("excludedTags[]")
        excludedTags: List<String>? = null,
        @Query("excludedTagsMode")
        excludedTagsMode: String? = null,
        @Query("status[]")
        status: List<String>? = null,
        @Query("originalLanguage[]")
        originalLanguage: List<String>? = null,
        @Query("excludedOriginalLanguage[]")
        excludedOriginalLanguage: List<String>? = null,
        @Query("availableTranslatedLanguage[]")
        availableTranslatedLanguage: List<String>? = null,
        @Query("publicationDemographic[]")
        publicationDemographic: List<String>? = null,
        @Query("ids[]")
        ids: List<String>? = null,
        @Query("contentRating[]")
        contentRating: List<String>? = null,
        @Query("createdAtSince")
        createdAtSince: String? = null,
        @Query("updatedAtSince")
        updatedAtSince: String? = null,
        @Query("order[latestUploadedChapter]")
        orderLatestUploadedChapter: String? = "desc",
        @Query("includes[]")
        includes: List<String>? = null,
        @Query("hasAvailableChapters")
        hasAvailableChapters: String? = null,
        @Query("hasUnavailableChapters")
        hasUnavailableChapters: String? = null
    ): Response<MangaListResponseDto>


    @GET("manga/{id}")
    suspend fun getMangaById(
        @Path("id")
        id: String,
        @Query("includes[]")
        includes: List<String>? = null
    ): Response<MangaResponseDto>


    @GET("mange/{id}/aggregate")
    suspend fun getMangaAggregate(
        @Path("id")
        mangaId: String,
        @Query("translatedLanguage[]")
        translatedLanguage: List<String>? = null,
        @Query("groups[]")
        groups: List<String>? = null,
        @Query("includeUnavailable")
        includeUnavailable: Int? = null
    ): Response<MangaAggregateResponseDto>


    @GET("manga/{id}/recommendation")
    suspend fun getMangaRecommendations(
        @Path("id")
        mangaId: String,
        @Query("includes[]")
        includes: List<String>? = null,
        @Query("order[score]")
        orderScore: String? = null,
        @Query("contentRating[]")
        contentRating: List<String>? = null
    ): Response<MangaRecommendationListResponseDto>


    @GET("manga/{id}/feed")
    suspend fun getMangaFeed(
        @Path("id")
        mangaId: String,
        @Query("limit")
        limit: Int? = 100,
        @Query("offset")
        offset: Int? = null,
        @Query("translatedLanguage[]")
        translatedLanguage: List<String>? = null,
        @Query("originalLanguage[]")
        originalLanguage: List<String>? = null,
        @Query("excludedOriginalLanguage[]")
        excludedOriginalLanguage: List<String>? = null,
        @Query("contentRating[]")
        contentRating: List<String>? = null,
        @Query("excludedGroups[]")
        excludedGroups: List<String>? = null,
        @Query("excludedUploaders[]")
        excludedUploaders: List<String>? = null,
        @Query("includeFutureUpdates")
        includeFutureUpdates: Int? = null,
        @Query("order[createdAt]")
        orderCreatedAt: String? = null,
        @Query("order[updatedAt]")
        orderUpdatedAt: String? = null,
        @Query("order[publishAt]")
        orderPublishAt: String? = null,
        @Query("order[readableAt]")
        orderReadableAt: String? = null,
        @Query("order[volume]")
        orderVolume: String? = null,
        @Query("order[chapter]")
        orderChapter: String? = null,
        @Query("includes[]")
        includes: List<String>? = null,
        @Query("includeEmptyPages")
        includeEmptyPages: Int? = null,
        @Query("includeFuturePublishAt")
        includeFuturePublishAt: Int? = null,
        @Query("includeExternalUrl")
        includeExternalUrl: Int? = null,
        @Query("includeUnavailable")
        includeUnavailable: Int? = null
    ): Response<MangaFeedResponceDto>


    @GET("manga/{id}/relation")
    suspend fun getMangaRelations(
        @Path("id")
        mangaId: String,
        @Query("includes[]")
        includes: List<String>? = null
    ): Response<MangaRelationListResponseDto>

    @GET("manga/tag")
    suspend fun mangaTeg(): Response<TagListResponseDto>


    @GET("group")
    suspend fun getGroups(
        @Query("limit")
        limit: Int? = 10,
        @Query("offset")
        offset: Int? = 0,
        @Query("ids[]")
        ids: List<String>? = null,
        @Query("name")
        name: String? = null,
        @Query("focusedLanguage")
        focusedLanguage: String? = null,
        @Query("includes[]")
        includes: List<String>? = null,
        @Query("order[latestUploadedChapter]")
        orderLatestUploadedChapter: String? = null
    ): Response<ScanlationGroupListResponseDto>


    @GET("group/{id}")
    suspend fun getGroupById(
        @Path("id")
        id: String,
        @Query("includes[]")
        includes: List<String>? = null
    ): Response<ScanlationGroupResponseDto>


}

