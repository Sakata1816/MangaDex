package com.example.MangaDex.domain.useCase.author

import com.example.MangaDex.domain.model.server.AuthorResponceModel
import com.example.MangaDex.domain.repository.server.MangaRepository
import com.example.MangaDex.domain.state.AuthorContentType
import com.example.MangaDex.domain.state.toApiValue
import javax.inject.Inject

class GetAuthorUseCase @Inject constructor(
    private val repository: MangaRepository
) {
    suspend operator fun invoke(
        id: String,
        contentType: AuthorContentType
    ):Result<AuthorResponceModel>{
        return repository.getAuthorById(
            id = id,
            includes = listOf(contentType.toApiValue())
        )
    }
}