package com.example.dota2.presentation.viewModel.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota2.domain.state.MangaFilters
import com.example.dota2.domain.useCase.manga.GetMangaTagsUseCase
import com.example.dota2.domain.useCase.manga.SearchMangaUseCase
import com.example.dota2.presentation.uiState.CatalogListUiState
import com.example.dota2.presentation.uiState.TagsUiState
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
class CatalogScreenViewModel @Inject constructor(
    private val searchManga: SearchMangaUseCase,
    private val getMangaTags: GetMangaTagsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(CatalogListUiState())
    val state = _state.asStateFlow()

    private val _tagState = MutableStateFlow(TagsUiState())
    val tagState = _tagState.asStateFlow()



    init {
        loadMangaList()
        viewModelScope.launch {
            _state
                .map { it.searchQuery }
                .distinctUntilChanged()
                .debounce(500)
                .drop(1) // пропускаем первый emit (начальный пустой запрос)
                .collect {
                    resetAndload()
                }
        }
    }



    fun loadMangaList(){
        val currentState = _state.value

        if(currentState.isLoading || currentState.endReached) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }


            searchManga(
                page = currentState.page,
                title = currentState.searchQuery,
                filters = currentState.filter
            )
                .onSuccess { response->
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


    fun loadTagList() {
        viewModelScope.launch {
            _tagState.update { it.copy(isLoading = true) }

            getMangaTags()
                .onSuccess { response->
                    _tagState.update {
                        it.copy(
                            tags = response.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
                .onFailure { throwable ->
                    _tagState.update {
                        it.copy(
                            error = throwable.message,
                            isLoading = false
                        )
                    }
                }
        }
    }


    fun resetAndload(){
        _state.update { it.copy(
            manga = emptyList(),
            isLoading =  false,
            endReached = false,
            error = null,
            page = 0
        ) }
        loadMangaList()
    }


    fun onSearchChange(search: String){
        _state.update { it.copy(searchQuery = search) }
    }


    fun onFiltersChanged(filters: MangaFilters) {
        _state.update { it.copy(filter = filters) }
        resetAndload()
    }


    fun resetFilters() {
        _state.update { it.copy(filter = MangaFilters()) }
        resetAndload()
    }


    fun onSortChange(sort: MangaFilters) {
        _state.update { it.copy(filter = sort) }
        resetAndload()
    }


}