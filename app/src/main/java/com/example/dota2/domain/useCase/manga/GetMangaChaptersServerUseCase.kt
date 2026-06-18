package com.example.dota2.domain.useCase.manga

import com.example.dota2.domain.repository.server.MangaRepository
import javax.inject.Inject

class GetMangaChaptersServerUseCase @Inject constructor(
    private val repository: MangaRepository
) {

    suspend operator fun invoke(chapterId: String): Result<List<String>?>{
        return repository.getChapterServer(chapterId)
            .map {response->

                val chapter = response.chapter ?: return@map emptyList()

                chapter.data.orEmpty().map { fileName ->
                    "${response.baseUrl}/data/${chapter.hash}/$fileName"
                }
        }
    }
}

