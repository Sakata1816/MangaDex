package com.example.dota2.data.local.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.dota2.data.local.entity.FavoriteMangaEntity
import com.example.dota2.data.local.entity.MangaStatus
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteMangaDao{

    @Query("SELECT * FROM favorite_manga WHERE userId = :userId")
    fun getAll(userId: String): Flow<List<FavoriteMangaEntity>>

    @Upsert
    suspend fun upsertAll(list: List<FavoriteMangaEntity>)

    @Upsert
    suspend fun upsertManga(entity: FavoriteMangaEntity)

    @Query("DELETE FROM favorite_manga WHERE userId = :userId")
    suspend fun deleteAll(userId: String)

    @Query("DELETE FROM favorite_manga WHERE id = :mangaId AND userId = :userId")
    suspend fun deleteById(mangaId: String, userId: String)

    @Transaction
    suspend fun syncAll(list: List<FavoriteMangaEntity>, userId: String) {
        deleteAll(userId)
        upsertAll(list)
    }

    @Query("SELECT * FROM favorite_manga WHERE altTitles LIKE '%' || :query || '%' AND userId = :userId")
    fun searchManga(query: String,userId: String): Flow<List<FavoriteMangaEntity>>

    @Query("""
    SELECT * FROM favorite_manga 
    WHERE userStatus = :status 
    AND userId = :userId
    AND LOWER(title) LIKE '%' || LOWER(:query) || '%'
""")
    fun getAnimeByStatus(status: MangaStatus, query: String,userId: String): Flow<List<FavoriteMangaEntity>>

}