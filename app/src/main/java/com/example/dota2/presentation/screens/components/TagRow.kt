package com.example.dota2.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dota2.domain.model.server.TagModel
import com.example.dota2.presentation.screens.TagsCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsSection(tags: List<TagModel>,onClick: (String, String, String) -> Unit) {
    FlowRow(
        modifier = Modifier
            .wrapContentHeight(),
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