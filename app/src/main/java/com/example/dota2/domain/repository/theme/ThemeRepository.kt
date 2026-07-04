package com.example.dota2.domain.repository.theme

import com.example.compose.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeRepository{
    val themeFlow: Flow<ThemeMode>
    suspend fun  setTheme(model:ThemeMode)
}