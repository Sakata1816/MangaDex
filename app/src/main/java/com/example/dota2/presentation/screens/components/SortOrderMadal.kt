package com.example.dota2.presentation.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.dota2.domain.state.MangaFilters
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dota2.domain.state.SortOrder


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaSortOrderCard(
    currentOrder: MangaFilters,
    onOrderSelected: (MangaFilters) -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Box {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { showSheet = true }) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "Сортировка"
                )
            }
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState
            ) {
                MangaSortSheetContent(
                    currentOrder = currentOrder,
                    onOrderSelected = onOrderSelected,
                    onClose = { showSheet = false }
                )
            }
        }
    }
}

@Composable
private fun MangaSortSheetContent(
    currentOrder: MangaFilters,
    onOrderSelected: (MangaFilters) -> Unit,
    onClose: () -> Unit
) {
    // Какое поле сейчас активно и в каком порядке — вытаскиваем из трёх nullable-полей модели
    val selectedField = currentOrder.selectedSortField()
    val selectedSortOrder = currentOrder.selectedSortOrder() ?: SortOrder.DESC

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Сортировать по",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))

        MangaSortField.entries.forEach { field ->
            SortRadioRow(
                label = field.label,
                isSelected = field == selectedField,
                onClick = {
                    // при выборе поля сохраняем уже выставленный порядок (или DESC по умолчанию)
                    onOrderSelected(currentOrder.withSort(field, selectedSortOrder))
                    onClose()
                }
            )
        }

        Spacer(Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Порядок",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))

        SortOrder.entries.forEach { order ->
            SortRadioRow(
                label = if (order == SortOrder.ASC) "По возрастанию" else "По убыванию",
                isSelected = selectedField != null && order == selectedSortOrder,
                enabled = selectedField != null,
                onClick = {
                    selectedField?.let { field ->
                        onOrderSelected(currentOrder.withSort(field, order))
                    }
                    onClose()
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Закрыть")
        }
    }
}

@Composable
private fun SortRadioRow(
    label: String,
    isSelected: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            enabled = enabled,
            onClick = onClick
        )
        Spacer(Modifier.width(8.dp))
        Text(label)
    }
}

// Абстракция "какое из трёх nullable-полей сейчас активно"
private enum class MangaSortField(val label: String) {
    LATEST_UPLOADED_CHAPTER("Последняя загруженная глава"),
    FOLLOWED_COUNT("Подписки"),
    RATING("Рейтинг")
}

private fun MangaFilters.selectedSortField(): MangaSortField? = when {
    orderLatestUploadedChapter != null -> MangaSortField.LATEST_UPLOADED_CHAPTER
    orderFollowedCount != null -> MangaSortField.FOLLOWED_COUNT
    orderRating != null -> MangaSortField.RATING
    else -> null
}

private fun MangaFilters.selectedSortOrder(): SortOrder? = when {
    orderLatestUploadedChapter != null -> orderLatestUploadedChapter
    orderFollowedCount != null -> orderFollowedCount
    orderRating != null -> orderRating
    else -> null
}

// Выставляет порядок для выбранного поля и обязательно сбрасывает остальные два в null,
// чтобы активным оставалось только одно поле сортировки
private fun MangaFilters.withSort(field: MangaSortField, order: SortOrder): MangaFilters {
    return copy(
        orderLatestUploadedChapter = if (field == MangaSortField.LATEST_UPLOADED_CHAPTER) order else null,
        orderFollowedCount = if (field == MangaSortField.FOLLOWED_COUNT) order else null,
        orderRating = if (field == MangaSortField.RATING) order else null
    )
}