package com.example.dota2.domain.repository.profile

import android.net.Uri
import com.example.dota2.data.remote.auth.dto.UserProfileDto
import com.example.dota2.domain.model.profile.UserProfileModel

interface ProfileRepository {

    suspend fun getUser(uid: String): Result<UserProfileModel?>

    suspend fun createUser(profile: UserProfileDto): Result<Unit>

    suspend fun updateProfile(uid: String, username: String, avatarUrl: String?): Result<Unit>


    suspend fun uploadAvatar(uid: String, uri: Uri): Result<String?>



}