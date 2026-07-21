package com.example.MangaDex.presentation.viewModel.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MangaDex.domain.useCase.manga.SearchMangaUseCase
import com.example.MangaDex.presentation.uiState.SearchScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchManga: SearchMangaUseCase
): ViewModel() {

    private val _state = MutableStateFlow(SearchScreenUiState())
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
            _state
                .map { it.query }
                .distinctUntilChanged()
                .debounce(500)
                .drop(1) // пропускаем первый emit (начальный пустой запрос)
                .collect {
                    resetAndload()
                }
        }
    }


   fun searchManga() {

        val currentState = _state.value
        if (currentState.isLoading || currentState.endReached) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            searchManga(currentState.page, currentState.query)
                .onSuccess { response ->
                    val newManga = response.data ?: emptyList()
                    _state.update { it.copy(
                        manga = it.manga + newManga,
                        isLoading = false,
                        endReached = newManga.isNullOrEmpty(),
                        page = it.page + 1
                    ) }
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


    fun onSearchChange(query: String){
        _state.update { it.copy(query = query) }
    }


    fun resetAndload(){
        _state.update { it.copy(
            manga = emptyList(),
            isLoading =  false,
            endReached = false,
            error = null,
            page = 0
        ) }
        searchManga()
    }


}