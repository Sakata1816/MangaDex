package com.example.dota2.domain.repository.profile

import com.example.dota2.data.local.entity.MangaStatus
import com.example.dota2.domain.model.profile.UserFavoriteMangaModel
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavorites(query: String): Flow<List<UserFavoriteMangaModel>>

    suspend fun addManga(anime: UserFavoriteMangaModel): Result<Unit>

    suspend fun syncFromFirestore(): Result<Unit>


    //LOCAL
    suspend fun deleteManga(id: String): Result<Unit>

    fun getMangaByStatus(status: MangaStatus, query: String): Flow<List<UserFavoriteMangaModel>>
}