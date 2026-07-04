package com.example.dota2.data.repository.theme

import com.example.compose.ThemeMode
import com.example.dota2.data.dataSource.theme.ThemePreferences
import com.example.dota2.domain.repository.theme.ThemeRepository
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