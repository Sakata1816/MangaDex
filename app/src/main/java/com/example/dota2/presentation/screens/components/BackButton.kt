package com.example.dota2.presentation.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBack: () -> Unit) {
    IconButton(
        onClick = onBack,
        modifier = modifier
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
    }
}