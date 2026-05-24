package com.example.dota2.domain.useCase.manga

import com.example.dota2.domain.model.server.TagListResponseModel
import com.example.dota2.domain.repository.server.MangaRepository
import javax.inject.Inject

class GetMangaTagsUseCase @Inject constructor(
    private val repository: MangaRepository
) {

    suspend operator fun invoke(): Result<TagListResponseModel>{
        return repository.mangaTeg()
    }
    
}