package com.example.MangaDex.presentation.screens.mangaScreens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.MangaDex.domain.model.server.MangaModel
import com.example.MangaDex.presentation.navigation.mainRoot.NavRoutes
import com.example.MangaDex.presentation.screens.extensions.getCoverUrl
import com.example.MangaDex.presentation.screens.extensions.getPreferredTitle
import com.example.MangaDex.presentation.uiState.SearchScreenUiState
import com.example.MangaDex.presentation.viewModel.screens.SearchScreenViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

// ─── Colors ───────────────────────────────────────────────────────────────────


// ─── Screen ───────────────────────────────────────────────────────────────────

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {

        SearchTopBar(
            query = state.query ?: "",
            onQueryChange = viewModel::onSearchChange,
            onClear = { viewModel.onSearchChange("") },
            onBack = { navController.popBackStack() },
            focusRequester = focusRequester
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 0.5.dp
        )

        SearchContent(
            state = state,
            onLoadMore = viewModel::searchManga,
            onMangaClick = {
                navController.navigate(NavRoutes.MangaDetail.getPath(it))
            }
        )
    }
}

// ─── Top Bar ──────────────────────────────────────────────────────────────────

@Composable
private fun SearchTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    onBack: () -> Unit,
    focusRequester: FocusRequester
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            decorationBox = { innerField ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {

                        if (query.isEmpty()) {
                            Text(
                                text = "Поиск манги...",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 16.sp
                            )
                        }

                        innerField()
                    }

                    AnimatedVisibility(
                        visible = query.isNotEmpty(),
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Очистить",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable(onClick = onClear)
                        )
                    }
                }
            }
        )
    }
}

// ─── Content ──────────────────────────────────────────────────────────────────

@Composable
private fun SearchContent(
    state: SearchScreenUiState,
    onLoadMore: () -> Unit,
    onMangaClick: (String) -> Unit
) {
    when {
        state.query!!.isEmpty() -> EmptyQueryState()

        state.isLoading && state.manga.isEmpty() -> LoadingState()

        state.manga.isEmpty() && !state.isLoading -> NoResultsState(query = state.query)

        else -> ResultsList(
            state = state,
            onLoadMore = onLoadMore,
            onMangaClick = onMangaClick
        )
    }



}


// ─── Results List ─────────────────────────────────────────────────────────────

@Composable
private fun ResultsList(
    state: SearchScreenUiState,
    onLoadMore: () -> Unit,
    onMangaClick: (String) -> Unit
) {
    val listState = rememberLazyListState()

    // Trigger pagination
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            lastVisible >= total - 3 && !state.isLoading && !state.endReached
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
                    onLoadMore()
                }
            }
    }
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {

        items(
            items = state.manga,
            key = { it.id }
        ) { manga ->
            MangaItem(
                manga = manga,
                onClick = { onMangaClick(manga.id) }
            )
        }

        if (state.isLoading) {
            items(3) { MangaItemSkeleton() }
        }
    }
}


// ─── Manga Item ───────────────────────────────────────────────────────────────

@Composable
private fun MangaItem(
    manga: MangaModel,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = manga.getCoverUrl(),
            contentDescription = manga.attributes?.altTitles.getPreferredTitle(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 100.dp, height = 145.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = manga.attributes?.status.orEmpty().uppercase(),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            )

            Text(
                text = manga.attributes?.altTitles.getPreferredTitle(),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 22.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

                manga.attributes?.year?.let {
                    Text(
                        text = it.toString(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
        }
    }
}

// ─── Skeleton ─────────────────────────────────────────────────────────────────

@Composable
private fun MangaItemSkeleton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(width = 100.dp, height = 145.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    }
}
// ─── Empty / Loading / No results ────────────────────────────────────────────

@Composable
private fun EmptyQueryState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(56.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Найти мангу",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Введите название манги в строку поиска",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LoadingState() {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
        repeat(5) { MangaItemSkeleton() }
    }
}

@Composable
private fun NoResultsState(query: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Ничего не найдено", color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "По запросу «$query» результатов нет",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 13.sp
        )
    }
}

