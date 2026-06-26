package com.example.dota2.data.dataSource.local

import com.example.dota2.data.local.DAO.FavoriteMangaDao
import com.example.dota2.data.local.entity.FavoriteMangaEntity
import com.example.dota2.data.local.entity.MangaStatus
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: FavoriteMangaDao
) {
    fun getAll(query: String,userId: String) = dao.searchManga(query,userId)
    suspend fun upsertAll(list: List<FavoriteMangaEntity>) = dao.upsertAll(list)
    suspend fun upsertManga(entity: FavoriteMangaEntity) = dao.upsertManga(entity)
    suspend fun deleteAll(userId: String) = dao.deleteAll(userId)
    suspend fun deleteById(mangaId: String, userId: String) = dao.deleteById(mangaId, userId)
    suspend fun syncAll(list: List<FavoriteMangaEntity>, userId: String) = dao.syncAll(list, userId)
    fun getMangaByStatus(status: MangaStatus, query: String, userId: String) = dao.getAnimeByStatus(status, query,userId)


}
