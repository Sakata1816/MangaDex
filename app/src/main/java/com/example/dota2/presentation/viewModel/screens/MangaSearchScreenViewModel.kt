package com.example.dota2.presentation.viewModel.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota2.domain.state.MangaFilters
import com.example.dota2.domain.useCase.manga.GetMangaTagsUseCase
import com.example.dota2.domain.useCase.manga.SearchMangaUseCase
import com.example.dota2.presentation.uiState.MangaSearchUiState
import com.example.dota2.presentation.uiState.TagsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class MangaSearchScreenViewModel @Inject constructor(
    private val SearchMangaUseCase: SearchMangaUseCase,
    private val getMangaTagsUseCase: GetMangaTagsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MangaSearchUiState())
    val state = _state.asStateFlow()

    private val _tagsState = MutableStateFlow(TagsUiState())
    val tagsState = _tagsState.asStateFlow()



    init {
        loadMangaList()
        getTagsList()
    }


    fun loadMangaList(reset: Boolean = false){

        val currentState = _state.value

        if(currentState.isLoading || currentState.endReached) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val page = if(reset) 0 else currentState.page

            SearchMangaUseCase(
                page = page,
                title = currentState.search,
                filters = currentState.filter
            ).onSuccess {response->

                val newList = if(reset) {
                    response.data?:emptyList()
                } else {
                    currentState.manga + (response.data?:emptyList())
                }
                    _state.update {

                        it.copy(
                            manga = newList,
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

    fun updateChange(search: String){
        _state.update { it.copy(
            search = search,
            page = 0,
            endReached = false
        )
        }
        loadMangaList(reset = true)
    }

    fun updateFilters(filter: MangaFilters){
        _state.update { it.copy(
            filter = filter,
            page = 0,
            endReached = false
        )
        }
        loadMangaList(reset = true)
    }


    fun getTagsList() {
        _tagsState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getMangaTagsUseCase()
                .onSuccess { response ->
                    _tagsState.update {
                        it.copy(
                            tags = response.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
                .onFailure { throwable ->
                    _tagsState.update {
                        it.copy(
                            error = throwable.message,
                            isLoading = false
                        )
                    }
                }
        }

    }

}
