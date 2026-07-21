package com.example.MangaDex.presentation.viewModel.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.ThemeMode
import com.example.MangaDex.domain.repository.theme.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val repository: ThemeRepository
): ViewModel() {


    val themeMode: StateFlow<ThemeMode> = repository.themeFlow
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ThemeMode.SYSTEM
        )

    fun changeTheme(mode: ThemeMode) {
        viewModelScope.launch {
            repository.setTheme(mode)
        }
    }
}