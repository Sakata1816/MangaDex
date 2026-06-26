package com.example.dota2.presentation.screens.animeScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dota2.data.local.entity.MangaStatus
import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.mapper.profile.toUi
import com.example.dota2.presentation.navigation.mainRoot.NavRoutes
import com.example.dota2.presentation.screens.components.ErrorBlock
import com.example.dota2.presentation.screens.components.MangaCardWithMenu
import com.example.dota2.presentation.screens.extensions.getCoverUrl
import com.example.dota2.presentation.screens.extensions.getPreferredTitle
import com.example.dota2.presentation.viewModel.auth.AuthViewModel
import com.example.dota2.presentation.viewModel.profile.FavoritesViewModel
import kotlinx.coroutines.launch


@Composable
fun FavoriteMangaScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
){

    val state by viewModel.state.collectAsState()

    val tabs = listOf("Смотрю", "Просмотрено", "Брошено", "Запланировано")
    val lists = listOf(
        state.watchingList,
        state.completedList,
        state.droppedList,
        state.plannedList)

    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.syncFromFirestore()
    }


    Column(
        modifier = Modifier.fillMaxSize()
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
                ) },
            trailingIcon = {
                if (state.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setSearch("") }) {
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


        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 16.dp,
            divider = {}
        ) {
            tabs.forEachIndexed { index, title->
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
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(Modifier.height(12.dp))
                        /*   Text(
                               text = "Список пуст",
                               color = MaterialTheme.colorScheme.onSurfaceVariant
                           )*/
                        ErrorBlock(error = state.error?:"FireStore error",
                            onRetry = {viewModel.syncFromFirestore()},
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
                            FavoriteMangaCard(
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


@Composable
fun FavoriteMangaCard(
    manga: MangaModel,
    onClick: (String) -> Unit,
    currentStatus: MangaStatus,
    onStatusChange: (MangaStatus) -> Unit
) {
    val statusColor = when (manga.attributes?.status?.lowercase()) {
        "ongoing"   -> Color(0xFF4CAF50)
        "completed" -> Color(0xFF2196F3)
        "hiatus"    -> Color(0xFFFF9800)
        "cancelled" -> Color(0xFFF44336)
        else        -> Color.Gray
    }

    val title = manga.attributes?.altTitles.getPreferredTitle()

    val tags = manga.attributes?.tags
        ?.take(3)
        ?.mapNotNull { it.attributes?.name?.get("en") }
        ?: emptyList()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick(manga.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top  // ← фиксирует MangaCardWithMenu сверху
        ) {
            // Обложка
            AsyncImage(
                model = manga.getCoverUrl(),
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(145.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(12.dp))

            // Основная информация — weight(1f) чтобы не выталкивать меню
            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),  // ← добавь это ,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Заголовок + статус пользователя
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.width(8.dp))
                    UserStatusChip(text = currentStatus.title ?: "None")
                }

                // Статус/год/демография — FlowRow вместо Row!
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    manga.attributes?.status?.let {
                        InfoChip(
                            text = it.replaceFirstChar(Char::uppercase),
                            color = statusColor
                        )
                    }
                    manga.attributes?.year?.let {
                        InfoChip(text = it.toString())
                    }
                    manga.attributes?.publicationDemographic?.let {
                        InfoChip(text = it.replaceFirstChar(Char::uppercase))
                    }
                }

                // Теги
                if (tags.isNotEmpty()) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        tags.forEach { tag -> InfoChip(text = tag) }
                    }
                }

                // Глава
                manga.attributes?.lastChapter?.let {
                    Text(
                        text = "Chapter $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.width(4.dp))

            MangaCardWithMenu(
                currentStatus = currentStatus,
                onStatusSelected = onStatusChange
            )
        }
    }
}


@Composable
private fun InfoChip(
    text: String,
    color: Color = MaterialTheme.colorScheme.surfaceVariant
) {

    Box(
        modifier = Modifier
            .background(
                color = color.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {

        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun UserStatusChip(
    text: String
) {

    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {

        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}