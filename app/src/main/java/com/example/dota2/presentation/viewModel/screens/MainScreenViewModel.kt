package com.example.dota2.presentation.viewModel.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota2.domain.model.server.MangaListResponseModel
import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.useCase.manga.GetMangaListUseCase
import com.example.dota2.presentation.uiState.MainListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getMangaUseCase: GetMangaListUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(MainListUiState())
    val state = _state.asStateFlow()

    init {
        loadMangaList()
    }


    private fun loadMangaList(){

        val currentState = _state.value

        if(currentState.isLoading || currentState.endReached) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getMangaUseCase(
                page = currentState.page
            )
                .onSuccess {response->

                    val newManga = response.data?:emptyList()

                    _state.update {
                        it.copy(
                            manga = it.manga + newManga ,
                            isLoading = false,
                            endReached = response.data.isNullOrEmpty() ,
                            page = it.page + 1
                        )
                    }
                }
                .onFailure {throwable->
                    _state.update {
                        it.copy(
                            error = throwable.message,
                            isLoading = false
                        )
                    }
                }
        }
    }

}
