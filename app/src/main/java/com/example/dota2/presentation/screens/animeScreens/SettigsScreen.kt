package com.example.dota2.presentation.screens.animeScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.dota2.presentation.navigation.mainRoot.NavRoutes
import com.example.dota2.presentation.screens.components.ErrorBlock
import com.example.dota2.presentation.viewModel.auth.AuthViewModel
import com.example.dota2.presentation.viewModel.profile.ProfileViewModel

@Composable
fun SettingsScreen(
     navController: NavController,
     viewModel: ProfileViewModel = hiltViewModel(),
     authViewModel: AuthViewModel = hiltViewModel()
){
     val state by viewModel.state.collectAsState()
     LaunchedEffect(Unit) {
          viewModel.loadProfile()
     }

     val nickname by remember(state.profile) { derivedStateOf { state.profile?.username ?: "" } }
     val email by remember(state.profile) { derivedStateOf { state.profile?.email ?: "" } }
     val avatarUrl by remember(state.profile) { derivedStateOf { state.profile?.avatarUrl ?: "" } }


     Column(
          modifier = Modifier
               .fillMaxSize()
               .padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
     ) {

          if(state.error!=null){
               ErrorBlock(error = state.error?:"неизвестная ошибка :Profile screen(UI)",
                    onRetry = {viewModel.loadProfile()},
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    content = {
                         Text(
                              text = "Ой, что-то пошло не так...",
                         )
                    }

               )
          }
          // Если профиль ещё не загрузился — показываем прогресс
          if (state.isLoading) {
               CircularProgressIndicator()
          } else {
               // Аватар
               if (avatarUrl.isNotEmpty()) {
                    Image(
                         painter = rememberAsyncImagePainter(avatarUrl),
                         contentDescription = null,
                         modifier = Modifier
                              .size(100.dp)
                              .clip(CircleShape)
                    )
               } else {
                    Box(
                         modifier = Modifier
                              .size(100.dp)
                              .clip(CircleShape)
                              .background(MaterialTheme.colorScheme.surfaceVariant),
                         contentAlignment = Alignment.Center
                    ) {
                         Text(
                              nickname.firstOrNull()?.uppercase() ?: "U",
                              color = MaterialTheme.colorScheme.onSurfaceVariant,
                              fontSize = 32.sp
                         )
                    }
               }
          }

          Spacer(Modifier.height(16.dp))

          // Ник
          Text(
               text = nickname,
               fontSize = 20.sp,
               color = MaterialTheme.colorScheme.onBackground,
               fontWeight = FontWeight.Bold,
          )

          Spacer(Modifier.height(4.dp))

          // Email
          Text(
               text = email,
               fontSize = 16.sp,
               color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
          )

          Spacer(Modifier.height(24.dp))

          // Изменить профиль
          Button(
               onClick = { navController.navigate(NavRoutes.ChangeProfile.route) },
               modifier = Modifier.fillMaxWidth()
          ) {
               Text("Изменить профиль")
          }

          Spacer(Modifier.height(12.dp))

          // Logout
          Button(
               onClick = { authViewModel.logout() },
               modifier = Modifier.fillMaxWidth(),
               colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
               )
          ) {
               Text("Выйти", color = Color.White)
          }

     }
}