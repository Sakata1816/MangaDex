package com.example.dota2.domain.useCase.chapter

import com.example.dota2.domain.model.server.ChapterResponseModel
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.AuthorContentType
import com.example.dota2.domain.state.toApiValue
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
