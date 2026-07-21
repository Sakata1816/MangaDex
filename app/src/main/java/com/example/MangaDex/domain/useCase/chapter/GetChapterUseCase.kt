package com.example.MangaDex.domain.useCase.chapter

import com.example.MangaDex.domain.model.server.ChapterResponseModel
import com.example.MangaDex.domain.repository.server.MangaRepository
import com.example.MangaDex.domain.state.AuthorContentType
import com.example.MangaDex.domain.state.toApiValue
import javax.inject.Inject

class GetChapterUseCase @Inject constructor(
    private val repository: MangaRepository
) {
    suspend operator fun invoke(
        id: String,
        contentType: List<AuthorContentType>
    ): Result<ChapterResponseModel> {
        return repository.getChapterById(
            id = id,
            includes = contentType.map { it.toApiValue() }
        )
    }
}
