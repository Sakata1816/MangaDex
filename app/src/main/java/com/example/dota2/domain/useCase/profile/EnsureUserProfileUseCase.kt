package com.example.dota2.domain.useCase.profile

import com.example.dota2.data.remote.auth.dto.UserProfileDto
import com.example.dota2.domain.model.profile.UserProfileModel
import com.example.dota2.domain.repository.profile.ProfileRepository
import com.example.dota2.mapper.profile.toModel
import javax.inject.Inject

class EnsureUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
     suspend operator fun invoke(uid: String, email: String): Result<UserProfileModel?> {
        return runCatching {
            val existingProfile = repository.getUser(uid).getOrThrow()
            existingProfile ?: run {
                val newProfile =
                    UserProfileDto(uid = uid, email = email) // можно добавить дефолтные поля
                repository.createUser(newProfile)
                newProfile.toModel()
            }
        }
    }
}