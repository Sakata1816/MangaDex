package com.example.MangaDex.presentation.screens.mangaScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.MangaDex.data.local.entity.MangaStatus
import com.example.MangaDex.domain.state.MangaFilters
import com.example.MangaDex.presentation.navigation.mainRoot.NavRoutes
import com.example.MangaDex.presentation.screens.components.BackButton
import com.example.MangaDex.presentation.screens.components.ErrorBlock
import com.example.MangaDex.presentation.viewModel.screens.MangaFilteredListViewModel
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaFilterList(
    type: String,
    typeId: String,
    title: String,
    viewModel: MangaFilteredListViewModel = hiltViewModel(),
    navController: NavController
) {

    val filter = remember(type, typeId) {
        when (type) {
            "tag" -> MangaFilters(includedTags = listOf(typeId))
            "author" -> MangaFilters(author = listOf(typeId))
            "artist" -> MangaFilters(artist = listOf(typeId))
            else -> MangaFilters()
        }
    }

    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    // Первая загрузка
    LaunchedEffect(filter) {
        viewModel.loadMangaFilteredList(filter)
    }

    // Пагинация
    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val totalItems = listState.layoutInfo.totalItemsCount

            lastVisible to totalItems
        }
            .distinctUntilChanged()
            .collect { (lastVisible, totalItems) ->

                if (
                    lastVisible >= totalItems - 5 &&
                    totalItems > 0 &&
                    !state.isLoading
                // && state.hasMore
                ) {
                    viewModel.loadMangaFilteredList(filter)
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        CenterAlignedTopAppBar(
            title = {
                Text(title)
            },
            navigationIcon = {
                BackButton(
                    onBack = { navController.popBackStack() }
                )
            }
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                top = 8.dp,
                bottom = 80.dp
            )
        ) {

            items(
                items = state.manga,
                key = { it.id }
            ) { manga ->

                FavoriteMangaRow(
                    manga = manga,
                    onClick = {
                        navController.navigate(
                            NavRoutes.MangaDetail.getPath(it)
                        )
                    },
                    currentStatus = MangaStatus.READING,
                    onStatusChange = {}
                )
            }

            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            state.error?.let { error ->
                item {
                    ErrorBlock(
                        error = error,
                        onRetry = {
                            viewModel.loadMangaFilteredList(filter)
                        },
                        modifier = Modifier.padding(16.dp),
                        content = {
                            Text("Ой, что-то пошло не так...")
                        }
                    )
                }
            }
        }
    }
}