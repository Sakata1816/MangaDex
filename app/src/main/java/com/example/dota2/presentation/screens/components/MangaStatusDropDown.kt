package com.example.dota2.presentation.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.dota2.data.local.entity.MangaStatus


fun MangaStatus.toColor(): Color = when (this) {
    MangaStatus.NONE -> Color.Gray
    MangaStatus.PLAN -> Color.LightGray
    MangaStatus.READING -> Color(0xFF2196F3)
    MangaStatus.DROPPED -> Color.Red
    MangaStatus.COMPLETED -> Color.Green
    MangaStatus.DELETED -> Color.Red
}
@Composable
fun MangaStatusDropdown(
    currentStatus: MangaStatus,
    onStatusSelected: (MangaStatus) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {

        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = currentStatus.toColor()
            )
        ) {
            Text(
                currentStatus.title
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            MangaStatus.values().drop(1).forEach { status ->

                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(status.toColor(), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(status.title)
                        }
                    },
                    onClick = {
                        expanded = false
                        onStatusSelected(status)
                    }
                )
                Divider()
            }


        }
    }
}

@Composable
fun StatusModal(
    visible: Boolean,
    currentStatus: MangaStatus,
    onDismiss: () -> Unit,
    onStatusSelected: (MangaStatus) -> Unit
){
    if (!visible) return



    Dialog(onDismissRequest = onDismiss) {

        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        Box(
            modifier = Modifier
                .width(screenWidth * 0.7f)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(16.dp)
        ) {
            Column (modifier = Modifier.fillMaxWidth()){

                Text(
                    text = "Status",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                MangaStatus.values().drop(1).forEach { status ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onStatusSelected(status)
                                onDismiss()
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        RadioButton(
                            selected = status == currentStatus,
                            onClick = {  onStatusSelected(status) }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = status.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cancel",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}