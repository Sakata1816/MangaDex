package com.example.dota2.data.repository.profile

import android.net.Uri
import com.example.dota2.data.dataSource.fireBase.ProfileDataSource
import com.example.dota2.data.remote.auth.dto.UserProfileDto
import com.example.dota2.domain.model.profile.UserProfileModel
import com.example.dota2.domain.repository.profile.ProfileRepository
import com.example.dota2.mapper.profile.toModel
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