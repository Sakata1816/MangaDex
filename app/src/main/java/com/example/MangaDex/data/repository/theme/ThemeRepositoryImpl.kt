package com.example.MangaDex.data.repository.theme

import com.example.compose.ThemeMode
import com.example.MangaDex.data.dataSource.theme.ThemePreferences
import com.example.MangaDex.domain.repository.theme.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val preferences: ThemePreferences
): ThemeRepository{

    override val themeFlow: Flow<ThemeMode> = preferences.themeFlow

    override suspend fun setTheme(mode: ThemeMode) {
        preferences.setTheme(mode)
    }
}