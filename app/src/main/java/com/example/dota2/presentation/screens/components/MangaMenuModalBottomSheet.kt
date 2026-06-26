package com.example.dota2.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.dota2.data.local.entity.MangaStatus


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaCardWithMenu(
    currentStatus: MangaStatus,
    onStatusSelected: (MangaStatus) -> Unit
) {

    var showSheet by remember { mutableStateOf(false) }
    var showStatusDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Box {
        // 🔹 Твоя карточка / контент
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { showSheet = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Меню"
                )
            }
        }

        // 🔹 Сам Bottom Sheet
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Text("Действия", style = MaterialTheme.typography.titleMedium)

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = {
                        // действие
                        showSheet = false
                    }) {
                        Text("Добавить в избранное")
                    }

                    TextButton(onClick = {
                        // действие
                        showSheet = false
                        showStatusDialog = true // ✅ вот это важно

                    }) {
                        Text("Изменить статус")
                    }


                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showSheet = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Закрыть")
                    }
                }
            }
        }
        // 🔹 Отдельно рендерим диалог
        StatusModal(
            visible = showStatusDialog,
            currentStatus = currentStatus,
            onDismiss = { showStatusDialog = false },
            onStatusSelected = {
                onStatusSelected(it)
                showStatusDialog = false
            }
        )
    }
}