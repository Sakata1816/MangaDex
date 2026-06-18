package com.example.dota2.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dota2.domain.state.MangaFilters
import com.example.dota2.presentation.navigation.mainRoot.NavRoutes
import com.example.dota2.presentation.screens.components.ErrorBlock
import com.example.dota2.presentation.viewModel.screens.CatalogScreenViewModel
import com.example.dota2.presentation.viewModel.screens.MainScreenViewModel
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: CatalogScreenViewModel
){



    val state by viewModel.state.collectAsState()

    val listState = rememberLazyListState()



    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ){

            Row() {
                Button(
                    onClick = {navController.navigate(NavRoutes.Filters.route)}
                ) {
                    Text(
                        "Filters"
                    )
                }
            }
        }

        OutlinedTextField(
            value = state.searchQuery ?: "",
            onValueChange = {
                viewModel.onSearchChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(50.dp),
            placeholder = { Text("Поиск...") },
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                ) },
            trailingIcon = {
                if (state.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.onSearchChange("") }) {
                        Icon(Icons.Default.Clear,
                            contentDescription = null)
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            textStyle = MaterialTheme.typography.bodyMedium
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
        ){
            items(state.manga){manga->
                MangaFilterCard(
                    manga,
                    onClick = {navController.navigate(NavRoutes.MangaDetail.getPath(it))}
                )
            }


            if(state.isLoading) {
                item{
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
                item{
                    ErrorBlock(
                        error = error,
                        onRetry = {viewModel.loadMangaList()},
                        modifier = Modifier.padding(16.dp),
                        content = {
                            Text(
                                text = "Ой, что-то пошло не так...",
                            )
                        }
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

                if (index != null  &&
                    index >= lastIndex - 5
                ) {
                    viewModel.loadMangaList()
                }
            }
    }

}