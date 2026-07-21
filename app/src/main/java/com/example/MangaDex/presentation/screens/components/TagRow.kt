package com.example.MangaDex.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.MangaDex.domain.model.server.TagModel
import com.example.MangaDex.presentation.screens.mangaScreens.TagsCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsSection(
    tags: List<TagModel>,
    onClick: (String, String, String) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            TagsCard(
                tag = tag,
                onClick = onClick
            )
        }
    }
}