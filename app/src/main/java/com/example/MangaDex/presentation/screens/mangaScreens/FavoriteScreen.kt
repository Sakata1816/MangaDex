package com.example.MangaDex.presentation.screens.mangaScreens

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.MangaDex.data.local.entity.MangaStatus
import com.example.MangaDex.domain.model.server.MangaModel
import com.example.MangaDex.mapper.profile.toUi
import com.example.MangaDex.presentation.navigation.mainRoot.NavRoutes
import com.example.MangaDex.presentation.screens.components.ErrorBlock
import com.example.MangaDex.presentation.screens.components.MangaCardWithMenu
import com.example.MangaDex.presentation.screens.extensions.getCoverUrl
import com.example.MangaDex.presentation.screens.extensions.getPreferredTitle
import com.example.MangaDex.presentation.viewModel.profile.FavoritesViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun FavoriteMangaScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val tabs = listOf("Смотрю", "Просмотрено", "Брошено", "Запланировано")
    val lists = listOf(
        state.watchingList,
        state.completedList,
        state.droppedList,
        state.plannedList
    )

    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.syncFromFirestore()
    }

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::syncFromFirestore
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {

            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.setSearch(it) },
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
                    )
                },
                trailingIcon = {
                    if (state.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setSearch("") }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = null
                            )
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


            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 16.dp,
                divider = {}
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            // 👇 При нажатии на таб — анимированно скроллим
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }


            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val pageList = lists[page]

                Box(modifier = Modifier.fillMaxSize()) {
                    if (pageList.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Spacer(Modifier.height(12.dp))
                            /*   Text(
                               text = "Список пуст",
                               color = MaterialTheme.colorScheme.onSurfaceVariant
                           )*/
                            ErrorBlock(
                                error = state.error ?: "FireStore error",
                                onRetry = { viewModel.syncFromFirestore() },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.BookmarkBorder,
                                        contentDescription = null,
                                        modifier = Modifier.size(64.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                content = {
                                    Text(
                                        text = "Список пуст",
                                    )
                                }
                            )

                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(pageList) { manga ->
                                FavoriteMangaRow(
                                    manga = manga.toUi(),
                                    onClick = { id ->
                                        navController.navigate(NavRoutes.MangaDetail.getPath(id))
                                    },
                                    currentStatus = manga.userStatus,
                                    onStatusChange = { newStatus ->
                                        viewModel.changeStatus(
                                            manga = manga, // или маппер
                                            status = newStatus
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun FavoriteMangaRow(
    manga: MangaModel,
    onClick: (String) -> Unit,
    currentStatus: MangaStatus,
    onStatusChange: (MangaStatus) -> Unit
) {
    val title = manga.attributes?.altTitles.getPreferredTitle()
    val lastChapter = manga.attributes?.lastChapter

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(manga.id) }
                .padding(horizontal = 4.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(12.dp))
            ,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = manga.getCoverUrl(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 100.dp, height = 145.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = manga.attributes?.status.orEmpty().uppercase(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(2.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = manga.type.orEmpty(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.sp
                    )

                    manga.attributes?.year?.let {

                        Text(
                            text = "•",
                            color = MaterialTheme.colorScheme.outline
                        )

                        Text(
                            text = it.toString(),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(Modifier.height(2.dp))

                Text(
                    text = if (lastChapter != null) "Глава $lastChapter" else "Нет глав",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            MangaCardWithMenu(
                currentStatus = currentStatus,
                onStatusSelected = onStatusChange
            )
        }

        HorizontalDivider(
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}