package com.example.dota2.domain.useCase.manga

import android.util.Log
import com.example.dota2.domain.repository.server.MangaRepository
import com.example.dota2.domain.state.MangaVolumeCovers
import javax.inject.Inject

class GetMangaCoversUseCase @Inject constructor(
    private val repository: MangaRepository
) {

    private companion object {
        val PAGE_SIZE = 100
    }

    suspend operator fun invoke(
        mangaId: String,
        page: Int = 0
    ): Result<List<MangaVolumeCovers>> {
        return repository.getCovers(
            limit = PAGE_SIZE,
            offset = page * PAGE_SIZE,
            mangaUid = listOf(mangaId),
            ids = null,
            locales = null,
            orderCreatedAt = null,
            orderUpdatedAt = null,
            orderVolume = null,
            includes = null
        )
            .also { result ->
            Log.d("CoversUseCase", "result: $result")
            result.onFailure { Log.e("CoversUseCase", "error", it) }
            result.onSuccess { Log.d("CoversUseCase", "success, data size: ${it.data?.size}") }
        }
            .map { response ->

            Log.d("CoversUseCase", "total data: ${response.data?.size}")
            Log.d("CoversUseCase", "first item: ${response.data?.firstOrNull()}")
            Log.d("CoversUseCase", "first fileName: ${response.data?.firstOrNull()?.attributes?.fileName}")


            response.data
                ?.groupBy { it.attributes?.volume }
                ?.map { (volume, covers) ->
                    MangaVolumeCovers(
                        volume = volume,
                        covers = covers.mapNotNull { cover ->
                            val fileName = cover.attributes?.fileName ?: return@mapNotNull null

                            Log.d("CoversUseCase", "building url: mangaId=$mangaId fileName=$fileName")
                            "https://uploads.mangadex.org/covers/$mangaId/$fileName"
                        }
                    )
                }
                ?.sortedWith(compareBy(nullsLast()) { it.volume?.toFloatOrNull() })
                .orEmpty()
        }
        }
}