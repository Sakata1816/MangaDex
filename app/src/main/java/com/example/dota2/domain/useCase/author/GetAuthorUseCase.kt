package com.example.dota2.domain.useCase.author

import com.example.dota2.domain.model.server.AuthorResponceModel
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.AuthorContentType
import com.example.dota2.domain.state.toApiValue
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