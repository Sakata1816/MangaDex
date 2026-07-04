package com.example.dota2.presentation.screens.mangaScreens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dota2.data.local.entity.MangaStatus
import com.example.dota2.domain.state.MangaFilters
import com.example.dota2.mapper.profile.toUi
import com.example.dota2.presentation.navigation.mainRoot.NavRoutes
import com.example.dota2.presentation.screens.components.ErrorBlock
import com.example.dota2.presentation.screens.components.MangaSortOrderCard
import com.example.dota2.presentation.uiState.CatalogListUiState
import com.example.dota2.presentation.viewModel.profile.FavoritesViewModel
import com.example.dota2.presentation.viewModel.screens.CatalogScreenViewModel
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: CatalogScreenViewModel,
    favoriteViewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val favorites by favoriteViewModel.getFavorites.collectAsState()
    val listState = rememberLazyListState()

    val favoriteMap = remember(favorites) {
        favorites.associateBy { it.id }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CatalogTopBar(
                state = state,
                onSearchChange = viewModel::onSearchChange,
                onOpenFilters = { navController.navigate(NavRoutes.Filters.route) },
                onSortChange = viewModel::onSortChange,
            )
        }
    ) { padding ->

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                top = padding.calculateTopPadding() + 8.dp,
                bottom = 80.dp
            )
        ) {
            items(state.manga) { manga ->
                val status = favoriteMap[manga.id]?.userStatus ?: MangaStatus.NONE
                FavoriteMangaCard(
                    manga,
                    onClick = { navController.navigate(NavRoutes.MangaDetail.getPath(it)) },
                    currentStatus = status,
                    onStatusChange = { newStatus ->
                        favoriteViewModel.changeStatus(
                            manga = manga.toUi(newStatus),
                            status = newStatus
                        )
                    }
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
                        onRetry = { viewModel.loadMangaList() },
                        modifier = Modifier.padding(16.dp),
                        content = { Text("Ой, что-то пошло не так...") }
                    )
                }
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { index ->
                val lastIndex = state.manga.lastIndex
                if (index != null && index >= lastIndex - 5) {
                    viewModel.loadMangaList()
                }
            }
    }
}


@Composable
private fun CatalogTopBar(
    state: CatalogListUiState,
    onSearchChange: (String) -> Unit,
    onOpenFilters: () -> Unit,
    onSortChange: (MangaFilters) -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilledTonalIconButton(onClick = onOpenFilters) {
                    Icon(Icons.Default.FilterList, contentDescription = "Фильтры")
                }
                Box(modifier = Modifier.weight(1f)) {
                    MangaSortOrderCard(state.filter, onSortChange)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("Поиск манги...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (state.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchChange("") }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )
        }
    }
}