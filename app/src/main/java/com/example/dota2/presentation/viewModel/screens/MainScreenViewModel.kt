package com.example.dota2.presentation.viewModel.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota2.domain.model.server.TagModel
import com.example.dota2.domain.state.MangaFilters
import com.example.dota2.domain.state.SortOrder
import com.example.dota2.domain.useCase.manga.GetMangaListUseCase
import com.example.dota2.domain.useCase.manga.GetMangaTagsUseCase
import com.example.dota2.domain.useCase.manga.SearchMangaUseCase
import com.example.dota2.presentation.uiState.MainListUiState
import com.example.dota2.presentation.uiState.TagsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getMangaListUseCase: GetMangaListUseCase,
    private val getMangaTagsUseCase: GetMangaTagsUseCase
): ViewModel() {

    private val _popularState = MutableStateFlow(
        MainListUiState(
            filters = MangaFilters(
                orderFollowedCount = SortOrder.DESC
            )
        )
    )

    private val _ratingState = MutableStateFlow(
        MainListUiState(
            filters = MangaFilters(
                orderRating = SortOrder.DESC
            )
        )
    )

    private val _lastUpdateState = MutableStateFlow(
        MainListUiState(
            filters = MangaFilters(
                orderLatestUploadedChapter = SortOrder.DESC
            )
        )
    )

    private val _tagsState = MutableStateFlow(TagsUiState())



    val popularState = _popularState.asStateFlow()
    val ratingState = _ratingState.asStateFlow()
    val lastUpdateState = _lastUpdateState.asStateFlow()
    val tagsState = _tagsState.asStateFlow()



    init {
        getTagsList()
        loadListByPopular()
        loadListByRating()
        loadListByLastUpdate()
    }






    fun loadListByPopular() = loadList(_popularState)
    fun loadListByRating() = loadList(_ratingState)
    fun loadListByLastUpdate() = loadList(_lastUpdateState)


    fun changePopularFilters(filters: MangaFilters) = changeFilters(_popularState, filters)
    fun changeRatingFilters(filters: MangaFilters) = changeFilters(_ratingState, filters)
    fun changeLastUpdateFilters(filters: MangaFilters) = changeFilters(_lastUpdateState, filters)


    private fun loadList(
        state: MutableStateFlow<MainListUiState>
    ) {
        val currentState = state.value
        if (currentState.isLoading || currentState.endReached) return

        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }

            getMangaListUseCase(
                page = currentState.page,
                filters = currentState.filters
            )
                .onSuccess { response ->
                    val newManga = response.data ?: emptyList()
                    state.update {
                        it.copy(
                            manga = it.manga + newManga,
                            isLoading = false,
                            endReached = newManga.isNullOrEmpty(),
                            page = it.page + 1
                        )
                    }

                }
                .onFailure { throwable ->
                    state.update {
                        it.copy(
                            error = throwable.message,
                            isLoading = false
                        )
                    }
                }
        }
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


    fun changeFilters(
        state: MutableStateFlow<MainListUiState>,
        filter: MangaFilters
    ) {
        state.update {
            it.copy(
                filters = filter,
                page = 0,
                manga = emptyList(),
                endReached = false
            )
        }
    }
}



