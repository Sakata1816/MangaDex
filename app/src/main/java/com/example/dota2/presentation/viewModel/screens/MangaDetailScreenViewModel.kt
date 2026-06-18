package com.example.dota2.presentation.viewModel.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota2.domain.useCase.manga.GetMangaDetailsByIdUseCase
import com.example.dota2.domain.useCase.manga.GetMangaChaptersUseCase
import com.example.dota2.domain.useCase.manga.GetMangaCoversUseCase
import com.example.dota2.domain.useCase.manga.GetMangaRelationsUseCase
import com.example.dota2.presentation.uiState.MangaDetailScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaDetailScreenViewModel @Inject constructor(
    private val getMangaByIdUseCase: GetMangaDetailsByIdUseCase,
    private val getMangaChaptersUseCase: GetMangaChaptersUseCase,
    private val getMangaRelationsUseCase: GetMangaRelationsUseCase,
    private val getMangaCoversUseCase: GetMangaCoversUseCase
): ViewModel() {


    private val _state = MutableStateFlow(MangaDetailScreenState())
    val state = _state.asStateFlow()


    fun getMangaDetail(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(detailLoading = true)}

            getMangaByIdUseCase(id)
                .onSuccess {response ->
                    _state.update {
                        it.copy(
                            manga = response.data,
                            detailLoading = false
                        )
                    }
                }
                .onFailure { throwable ->
                    _state.update {
                        it.copy(
                            detailError = throwable.message,
                            detailLoading = false
                        )
                    }

                }
        }
    }

    fun getMangaRelations(mangaId: String) {
        val currentState = state.value
        if (currentState.relationsLoading|| currentState.relationsEndReached) return

        viewModelScope.launch {
            _state.update { it.copy(relationsLoading = true) }

            getMangaRelationsUseCase(
                mangaId= mangaId,
                page = currentState.relationsPage
            )
                .onSuccess { response->
                    val newManga = response.data ?: emptyList()
                    _state.update {
                        it.copy(
                            relations = it.relations + newManga,
                            relationsLoading = false,
                            relationsEndReached = newManga.isNullOrEmpty(),
                            relationsPage = it.relationsPage + 1
                        )
                    }
                }
                .onFailure { throwable ->
                    _state.update {
                        it.copy(
                            relationsError = throwable.message,
                            relationsLoading = false
                        )
                    }
                }
        }
    }


    fun getMangaChapters(mangaId: String) {
        val currentState = _state.value

        if(currentState.chaptersEndReached || currentState.chaptersLoading) return
        _state.update { it.copy(chaptersLoading = true) }

        viewModelScope.launch {
            getMangaChaptersUseCase(
                mangaId = mangaId,
                page = currentState.chaptersPage
                )
                .onSuccess { response ->

                    if(response == null)
                        _state.update { it.copy(
                            chaptersLoading = false,
                            chaptersEndReached = true
                        )
                        return@onSuccess}

                    val newChapters = response?:emptyList()

                    _state.update {current->

                        val merged = (current.chapters + newChapters)
                            .groupBy { it.chapter }
                            .map { (chapterNum,duplicates)->
                                duplicates.first().copy(
                                    translations = duplicates.flatMap { it.translations }
                                )
                            }
                            .sortedByDescending { it.chapter?.toFloatOrNull() }

                        current.copy(
                        chapters =  merged,
                        chaptersLoading = false,
                        chaptersEndReached = response.isNullOrEmpty(),
                        chaptersPage = current.chaptersPage + 1
                    )
                    }
                }
                .onFailure { throwable ->
                    _state.update {
                        it.copy(
                            chaptersError = throwable.message,
                            chaptersLoading = false
                        )
                    }
                }
        }
    }


    fun getMangaCovers(mangaId: String){
        val currentState = _state.value
        if(currentState.coversEndReached || currentState.coversLoading) return

        viewModelScope.launch {
            _state.update { it.copy(coversLoading = true) }

            getMangaCoversUseCase(
                mangaId,
                currentState.coversPage
            )
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            covers = it.covers + response,
                            coversLoading = false,
                            coversEndReached = response.isNullOrEmpty(),
                            coversPage = it.coversPage + 1
                        )
                    }
                }
                .onFailure { throwable ->
                    _state.update {
                        it.copy(
                            coversError = throwable.message,
                            coversLoading = false
                        )
                    }
                }
        }
    }


}