package com.example.dota2.data.dataSource.fireBase

import com.example.dota2.data.remote.auth.dto.UserFavoriteMangaDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoriteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private fun favoritesRef() =
        firestore.collection("users")
            .document(auth.uid!!)
            .collection("favorites")



    suspend fun fetchAll(): List<UserFavoriteMangaDto> {
        val user = auth.currentUser ?: return emptyList()
        return firestore.collection("users")
            .document(user.uid)
            .collection("favorites")
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(UserFavoriteMangaDto::class.java) }
    }


    suspend fun addManga(manga: UserFavoriteMangaDto) {
        favoritesRef()
            .document(manga.id)
            .set(manga)
    }

    suspend fun deleteManga(id: String) {
        favoritesRef()
            .document(id)
            .delete()
    }

}