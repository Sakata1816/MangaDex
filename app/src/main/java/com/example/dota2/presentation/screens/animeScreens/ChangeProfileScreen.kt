package com.example.dota2.presentation.screens.animeScreens

import android.app.Activity
import android.net.Uri
import android.widget.Button
import android.widget.Space
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.dota2.presentation.screens.components.BackButton
import com.example.dota2.presentation.viewModel.profile.ProfileViewModel
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun ChangeProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val profile = state.profile

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var nickname by remember { mutableStateOf("") }

    LaunchedEffect(state) {          // ключ — стабильный id, не весь объект
        profile?.username?.let { nickname = it }
    }

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    LaunchedEffect(viewModel.isSaved) {
        if (viewModel.isSaved) {
            viewModel.resetState()
            navController.popBackStack()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri   // 👈 только для превью
        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .height(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(
                onBack = { navController.popBackStack() }
            )
        }

        val imageToShow = selectedImageUri ?: profile?.avatarUrl

        if (imageToShow != null) {
            Image(
                painter = rememberAsyncImagePainter(imageToShow),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable { launcher.launch("image/*") }
            )
        } else {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    nickname.firstOrNull()?.uppercase() ?: "U",
                    color = Color.White,
                    fontSize = 32.sp
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        TextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("Set nickname") }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                // 👇 Сохраняем ВСЕГДА — даже если фото не менялось
                viewModel.updateProfile(nickname, selectedImageUri)
            },
            enabled = !state.isLoading && nickname.isNotBlank()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Text("Сохранить профиль")
            }
        }

    }
}
