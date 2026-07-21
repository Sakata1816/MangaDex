package com.example.MangaDex.presentation.viewModel.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MangaDex.domain.useCase.manga.GetMangaChaptersServerUseCase
import com.example.MangaDex.presentation.uiState.ReadMode
import com.example.MangaDex.presentation.uiState.ReaderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReaderScreenViewModel @Inject constructor(
    private val getMangaChaptersServerUseCase: GetMangaChaptersServerUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ReaderUiState())
    val state = _state.asStateFlow()



    fun getChapterServer(chapterId: String){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getMangaChaptersServerUseCase(chapterId)
                .onSuccess {response->
                    _state.update { it.copy(
                        isLoading = false,
                        pages = response.orEmpty()
                    )
                    }
        }
                .onFailure { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = throwable.message
                        )
                    }
                }
        }
    }


    fun setReadMode(readMode: ReadMode){
        _state.update { it.copy(readMode = readMode) }
    }


    }

