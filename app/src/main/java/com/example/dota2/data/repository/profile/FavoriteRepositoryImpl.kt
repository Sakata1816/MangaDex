package com.example.dota2.data.repository.profile

import com.example.dota2.data.dataSource.fireBase.AuthDataSource
import com.example.dota2.data.dataSource.fireBase.FavoriteDataSource
import com.example.dota2.data.dataSource.local.LocalDataSource
import com.example.dota2.data.local.entity.MangaStatus
import com.example.dota2.domain.model.profile.UserFavoriteMangaModel
import com.example.dota2.domain.repository.profile.FavoriteRepository
import com.example.dota2.mapper.profile.toDomain
import com.example.dota2.mapper.profile.toDto
import com.example.dota2.mapper.profile.toEntity
import com.example.dota2.mapper.profile.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val fireBase: FavoriteDataSource,
    private val local: LocalDataSource,
    private val auth: AuthDataSource

): FavoriteRepository{


    private val currentUserId get() = auth.getCurrentUser()?.uid

    override fun getFavorites(query: String): Flow<List<UserFavoriteMangaModel>> {
        val userId = currentUserId ?: return kotlinx.coroutines.flow.flowOf(emptyList())
        return local.getAll(query,userId).map { list ->
            list.map { it.toDomain() }
        }
    }



    override suspend fun syncFromFirestore(): Result<Unit> {
        return try {
            val remoteList = fireBase.fetchAll()

            if (remoteList == null) {
                return Result.failure(Exception("Remote data is null"))
            }

            local.syncAll(remoteList.map { it.toModel().toEntity(currentUserId?:"User not logged in" ) }, currentUserId?:"User not logged in" )
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun addManga(manga: UserFavoriteMangaModel): Result<Unit> {
        return try {
            fireBase.addManga(manga.toDto())
            local.upsertManga(manga.toEntity(currentUserId?:"User not logged in"))
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(Exception("Failed to add anime", e))
        }
    }

    override suspend fun deleteManga(id: String): Result<Unit> {
        return try {
            fireBase.deleteManga(id)
            local.deleteById( id, currentUserId?:"User not logged in")
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(Exception("Failed to delete anime", e))
        }
    }




    // ---------------- LOCAL ----------------


    override fun getMangaByStatus(status: MangaStatus,query: String): Flow<List<UserFavoriteMangaModel>> =
        local.getMangaByStatus(status,query,currentUserId?:"User not logged in").map { list ->
            list.map { it.toDomain() }
        }

}