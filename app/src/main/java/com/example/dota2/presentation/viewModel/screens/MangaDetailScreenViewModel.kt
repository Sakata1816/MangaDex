package com.example.dota2.presentation.viewModel.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota2.domain.useCase.manga.GetMangaDetailsByIdUseCase
import com.example.dota2.domain.useCase.manga.GetMangaChaptersUseCase
import com.example.dota2.presentation.uiState.MangaDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaDetailScreenViewModel @Inject constructor(
    private val getMangaByIdUseCase: GetMangaDetailsByIdUseCase,
    private val getMangaChaptersUseCase: GetMangaChaptersUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MangaDetailUiState())
    val state: StateFlow<MangaDetailUiState> = _state.asStateFlow()




    fun getMangaDetail(id: String) {

        _state.update { it.copy(mangaIsLoading = true) }

        viewModelScope.launch {
            getMangaByIdUseCase(id)
                .onSuccess {response ->
                    _state.update { it.copy(
                        details = response.data,
                        mangaIsLoading = false)
                    }
                }
                .onFailure { throwable ->
                    _state.update { it.copy(
                        mangaIsLoading = false,
                        detailError = throwable.message)
                    }
                }
        }
    }


    fun getMangaChapters(mangaId: String) {
        val currentState = _state.value

        _state.update { it.copy(chaptersAreLoading = true) }

        if(currentState.endReached || currentState.chaptersAreLoading) return

        viewModelScope.launch {
            getMangaChaptersUseCase(
                mangaId = mangaId,
                page = currentState.page
                )
                .onSuccess { response ->

                    val newChapters = response.data?: emptyList()

                    _state.update { it.copy(
                        chapters =  it.chapters + newChapters,
                        chaptersAreLoading = false,
                        endReached = response.data.isNullOrEmpty(),
                        page = it.page + 1
                    )
                    }
                }
                .onFailure { throwable ->
                    _state.update { it.copy(
                        chapterError = throwable.message,
                        chaptersAreLoading = false
                    )
                    }
                }
        }

    }

}