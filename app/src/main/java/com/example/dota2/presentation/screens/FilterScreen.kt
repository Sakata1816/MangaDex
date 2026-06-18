package com.example.dota2.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.dota2.domain.state.ContentRating
import com.example.dota2.domain.state.MangaFilters
import com.example.dota2.domain.state.PublicationDemographic
import com.example.dota2.domain.state.Status



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    currentFilters: MangaFilters,
    onOpenTags: () -> Unit,
    onApply: (MangaFilters) -> Unit,
    onBack: () -> Unit
) {
    var draft by remember(currentFilters) { mutableStateOf(currentFilters) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Фильтры") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { draft = MangaFilters() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Сбросить")
                }
                Button(
                    onClick = { onApply(draft) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Применить")
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item {
                FilterRow(
                    title = "Теги",
                    subtitle = if (draft.includedTags.isNullOrEmpty()) {
                        "Все"
                    } else {
                        "${draft.includedTags!!.size} выбрано"
                    },
                    onClick = { onOpenTags() }
                )
                HorizontalDivider()
            }

            item {
                SectionHeader("Статус")
                MultiSelectChips(
                    options = Status.entries,
                    selected = draft.status ?: emptyList(),
                    label = { it.label() },
                    onToggle = { status, isSelected ->
                        val current = draft.status ?: emptyList()
                        draft = draft.copy(
                            status = (if (isSelected) current + status else current - status)
                                .ifEmpty { null }
                        )
                    }
                )
            }

            item {
                SectionHeader("Возрастной рейтинг")
                MultiSelectChips(
                    options = ContentRating.entries,
                    selected = draft.contentRating ?: emptyList(),
                    label = { it.label() },
                    onToggle = { rating, isSelected ->
                        val current = draft.contentRating ?: emptyList()
                        draft = draft.copy(
                            contentRating = (if (isSelected) current + rating else current - rating)
                                .ifEmpty { null }
                        )
                    }
                )
            }

            item {
                SectionHeader("Демография")
                MultiSelectChips(
                    options = PublicationDemographic.entries,
                    selected = draft.publicationDemographic ?: emptyList(),
                    label = { it.label() },
                    onToggle = { demo, isSelected ->
                        val current = draft.publicationDemographic ?: emptyList()
                        draft = draft.copy(
                            publicationDemographic = (if (isSelected) current + demo else current - demo)
                                .ifEmpty { null }
                        )
                    }
                )
            }

            item {
                SectionHeader("Год выпуска")
                OutlinedTextField(
                    value = draft.year?.toString() ?: "",
                    onValueChange = { text ->
                        draft = draft.copy(year = text.filter { it.isDigit() }.take(4).toIntOrNull())
                    },
                    placeholder = { Text("Например, 2024") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            item {
                SectionHeader("Доступность")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .toggleable(
                            value = draft.availableChapter,
                            role = Role.Checkbox,
                            onValueChange = { draft = draft.copy(availableChapter = it) }
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = draft.availableChapter, onCheckedChange = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Только с доступными главами")
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    )
}

@Composable
private fun FilterRow(title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.bodyLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
private fun <T> MultiSelectChips(
    options: List<T>,
    selected: List<T>,
    label: (T) -> String,
    onToggle: (T, Boolean) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            val isSelected = option in selected
            FilterChip(
                selected = isSelected,
                onClick = { onToggle(option, !isSelected) },
                label = { Text(label(option)) }
            )
        }
    }
}

private fun Status.label(): String = when (this) {
    Status.ONGOING -> "Онгоинг"
    Status.COMPLETED -> "Завершён"
    Status.HIATUS -> "Приостановлен"
    Status.CANCELLED -> "Отменён"
}

private fun ContentRating.label(): String = when (this) {
    ContentRating.SAFE -> "Safe"
    ContentRating.SUGGESTIVE -> "Suggestive"
    ContentRating.EROTICA -> "Erotica"
    ContentRating.PORNOGRAPHIC -> "Pornographic"
}

private fun PublicationDemographic.label(): String = when (this) {
    PublicationDemographic.SHOUJO -> "Shoujo"
    PublicationDemographic.JOSEI -> "Josei"
    PublicationDemographic.SEINEN -> "Seinen"
    PublicationDemographic.NONE -> "Без демографии"
}