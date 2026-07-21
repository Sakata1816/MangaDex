package com.example.MangaDex.presentation.viewModel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MangaDex.data.local.entity.MangaStatus
import com.example.MangaDex.domain.model.profile.UserFavoriteMangaModel
import com.example.MangaDex.domain.repository.profile.FavoriteRepository
import com.example.MangaDex.presentation.screens.extensions.getPreferredDescription
import com.example.MangaDex.presentation.uiState.FavoriteMangaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteRepository
): ViewModel() {

    private val _state = MutableStateFlow(FavoriteMangaUiState())
    val state = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()



    init {
        // Синхронизируем _searchQuery со state
        viewModelScope.launch {
            _state
                .map { it.searchQuery }
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    _state.update { it.copy(searchQuery = query) }
                }
        }

        // Подписываемся на списки по статусу
        observeList(MangaStatus.READING) { list, s -> s.copy(watchingList = list) }
        observeList(MangaStatus.COMPLETED) { list, s -> s.copy(completedList = list) }
        observeList(MangaStatus.DROPPED) { list, s -> s.copy(droppedList = list) }
        observeList(MangaStatus.PLAN) { list, s -> s.copy(plannedList = list) }
    }



    private fun observeList(
        status: MangaStatus,
        updater:(List<UserFavoriteMangaModel>, FavoriteMangaUiState) -> FavoriteMangaUiState
    ){
        viewModelScope.launch {
            _state
                .map { it.searchQuery }
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query -> repository.getMangaByStatus(status,query) }
                .collect{list->
                    _state.update{updater(list,state.value)}
        }
        }
    }


    val getFavorites = combine(
        repository.getFavorites(state.value.searchQuery),
        _state.map { it.searchQuery }
    ) { list, query ->
        list.filter { manga ->
            query.isBlank() || manga.title.getPreferredDescription().contains(query, ignoreCase = true)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList<UserFavoriteMangaModel>()
    )


    fun syncFromFirestore(){
        viewModelScope.launch {
            _state.update { it.copy(
                isRefreshing = true,
                error = null) }
            repository.syncFromFirestore().fold(
                onSuccess = {
                    _state.update { it.copy(
                        isRefreshing = false
                    ) }
                },
                onFailure = {Throwable->
                    _state.update { it.copy(
                        isRefreshing = false,
                        error = Throwable.message?:"error on sync..")
                    }
                }
            )
        }
    }


    fun setSearch(query: String) {
        _state.update {
            it.copy(searchQuery = query)
        }
    }


    fun changeStatus(
        manga: UserFavoriteMangaModel,
        status: MangaStatus
    ){
        viewModelScope.launch {
            if(status== MangaStatus.NONE || status == MangaStatus.DELETED){
                repository.deleteManga(manga.id)
            }else{
                repository.addManga(manga.copy(
                    userStatus = status)
                )
            }
        }
    }



}