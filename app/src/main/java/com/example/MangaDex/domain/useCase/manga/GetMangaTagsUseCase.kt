package com.example.MangaDex.domain.useCase.manga

import com.example.MangaDex.domain.model.server.TagListResponseModel
import com.example.MangaDex.domain.repository.server.MangaRepository
import javax.inject.Inject

class GetMangaTagsUseCase @Inject constructor(
    private val repository: MangaRepository
) {

    suspend operator fun invoke(): Result<TagListResponseModel>{
        return repository.mangaTeg()
    }
    
}