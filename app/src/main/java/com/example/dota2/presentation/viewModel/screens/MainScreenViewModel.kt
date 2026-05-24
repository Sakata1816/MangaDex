package com.example.dota2.presentation.viewModel.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota2.domain.useCase.manga.SearchMangaUseCase
import com.example.dota2.presentation.uiState.MainListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val searchScreen: SearchMangaUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MainListUiState())
    val state = _state.asStateFlow()


    private fun loadList(){
        val currentState = _state.value

        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            searchScreen(page = currentState.page,
                )

        }

    }


}