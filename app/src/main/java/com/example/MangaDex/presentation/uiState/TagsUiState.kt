package com.example.MangaDex.presentation.uiState

import com.example.MangaDex.domain.model.server.TagModel

data class TagsUiState(
    val tags: List<TagModel> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false

    )