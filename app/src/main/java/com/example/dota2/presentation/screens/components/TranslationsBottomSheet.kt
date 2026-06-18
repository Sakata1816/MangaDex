package com.example.dota2.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dota2.domain.model.server.ChapterModel
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