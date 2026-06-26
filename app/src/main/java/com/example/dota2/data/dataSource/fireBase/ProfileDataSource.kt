package com.example.dota2.data.dataSource.fireBase

import android.net.Uri
import android.util.Log
import com.example.dota2.data.remote.auth.dto.UserProfileDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileDataSource @Inject constructor(
    private val profile: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    private val usersCollection = profile.collection("users")

    suspend fun getUser(uid: String) =
        usersCollection.document(uid)
            .get()
            .await() // возвращает DocumentSnapshot


    suspend fun createUser(profile: UserProfileDto) =
        usersCollection.document(profile.uid).set(profile).await()

    suspend fun updateUser(uid: String, updates: Map<String, String?>) =
        usersCollection.document(uid).update(updates).await()


    suspend fun uploadAvatar(uid: String, uri: Uri): String {
        Log.d("Storage", "uid: $uid")
        Log.d("Storage", "auth: ${FirebaseAuth.getInstance().currentUser?.uid}")

        val ref = storage.reference.child("avatars/$uid.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()

    }

}

