package com.example.MangaDex.domain.useCase.profile

import com.example.MangaDex.domain.repository.profile.ProfileRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(uid: String, username: String, avatarUrl: String?): Result<Unit> {
        if (username.isBlank()) return Result.failure(Exception("Имя не может быть пустым"))
        if (username.length < 3) return Result.failure(Exception("Минимум 3 символа"))

        return repository.updateProfile(uid, username, avatarUrl)
    }
}