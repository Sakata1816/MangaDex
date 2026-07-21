package com.example.MangaDex.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.MangaDex.domain.model.server.ChapterModel
import kotlin.collections.forEach


@Composable
fun TranslationBottomSheet(
    translations: List<ChapterModel>,
    onClick: (chapterId:String) -> Unit
){
    Column(modifier = Modifier
        .padding(horizontal =  16.dp)
        .padding(bottom = 32.dp)
    ) {
        Text(
            text = "Select Language",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        translations.forEach { translation ->
            val language = translation.attributes?.translatedLanguage ?: "unknown language"
            val chapterId = translation.id

            ListItem(
                headlineContent = { Text(text = language.uppercase()) },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable { onClick(chapterId ?: "") }
            )

        }
    }
}