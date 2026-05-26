package com.example.dota2.presentation.uiState

import com.example.dota2.domain.model.server.TagModel

data class TagsUiState(
    val tags: List<TagModel> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false

    )