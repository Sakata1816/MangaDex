package com.example.MangaDex.data.repository.profile

import android.net.Uri
import com.example.MangaDex.data.dataSource.fireBase.ProfileDataSource
import com.example.MangaDex.data.remote.auth.dto.UserProfileDto
import com.example.MangaDex.domain.model.profile.UserProfileModel
import com.example.MangaDex.domain.repository.profile.ProfileRepository
import com.example.MangaDex.mapper.profile.toModel
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val fireBase: ProfileDataSource
): ProfileRepository{

    override  suspend fun getUser(uid: String): Result<UserProfileModel?>  {
        return runCatching {
            val doc = fireBase.getUser(uid).toObject(UserProfileDto::class.java)
            doc?.toModel()
        }
    }



    override  suspend fun createUser(profile: UserProfileDto): Result<Unit> {
        return runCatching {
            fireBase.createUser(profile)
        }
    }


    override suspend fun updateProfile(uid: String, username: String, avatarUrl: String?): Result<Unit> {
        return runCatching {
            fireBase.updateUser(
                uid,
                mapOf(
                    "username" to username,
                    "avatarUrl" to avatarUrl
                )
            )
        }
    }


    override suspend fun uploadAvatar(uid: String, uri: Uri): Result<String>{
        return runCatching {
            fireBase.uploadAvatar(uid, uri)
        }
    }



}