package com.example.dota2.presentation.viewModel.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota2.domain.state.MangaFilters
import com.example.dota2.domain.useCase.manga.GetMangaListUseCase
import com.example.dota2.presentation.uiState.MainListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaFilteredListViewModel @Inject constructor(
    private val getMangaByIdUseCase: GetMangaListUseCase
): ViewModel(){

    private val _state = MutableStateFlow(MainListUiState())
    val state = _state.asStateFlow()


    fun loadMangaFilteredList(filter: MangaFilters){

        val currentState = _state.value

        if(currentState.isLoading || currentState.endReached) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getMangaByIdUseCase(
                page = currentState.page,
                filters = filter
            )
                .onSuccess { response ->
                    val newManga = response.data ?: emptyList()
                    _state.update { it.copy(
                        manga = it.manga + newManga,
                        isLoading = false,
                        endReached = newManga.isNullOrEmpty(),
                        page = it.page + 1
                    )
                    }
                }
                .onFailure { throwable ->
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