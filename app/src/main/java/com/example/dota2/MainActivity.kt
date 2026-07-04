package com.example.dota2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.compose.AppTheme
import com.example.dota2.presentation.navigation.authRoot.RootScreen
import com.example.dota2.presentation.viewModel.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val themeMode by themeViewModel.themeMode.collectAsState()


            AppTheme(
                themeMode = themeMode
            ) {
                RootScreen()
            }

        }


    }
}
