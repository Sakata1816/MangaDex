package com.example.dota2.presentation.uiState

data class ReaderUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val pages: List<String> = emptyList(),
    val page: Int = 0,
    val readMode: ReadMode = ReadMode.Vertical
)

enum class ReadMode{
    Vertical,
    LeftToRight,
    RightToLeft
}

