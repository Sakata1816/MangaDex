package com.example.MangaDex.presentation.screens.mangaScreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.MangaDex.domain.model.server.MangaModel
import com.example.MangaDex.domain.state.MangaVolumeChaptersState
import com.example.MangaDex.domain.state.MangaVolumeCovers
import com.example.MangaDex.presentation.navigation.mainRoot.NavRoutes
import com.example.MangaDex.presentation.screens.components.BackButton
import com.example.MangaDex.presentation.screens.components.ErrorBlock
import com.example.MangaDex.presentation.screens.components.ExpandableDescription
import com.example.MangaDex.presentation.screens.components.TranslationBottomSheet
import com.example.MangaDex.presentation.screens.components.TagsSection
import com.example.MangaDex.presentation.screens.extensions.getCoverUrl
import com.example.MangaDex.presentation.screens.extensions.getPreferredDescription
import com.example.MangaDex.presentation.screens.extensions.getPreferredTitle
import com.example.MangaDex.presentation.uiState.MangaDetailScreenState
import com.example.MangaDex.presentation.viewModel.screens.MangaDetailScreenViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaDetailScreen(
    mangaId: String,
    navController: NavController,
    viewModel: MangaDetailScreenViewModel = hiltViewModel()
) {

    LaunchedEffect(mangaId) {
        viewModel.getMangaDetail(mangaId)
        viewModel.getMangaRelations(mangaId)
        viewModel.getMangaCovers(mangaId)
        viewModel.getMangaChapters(mangaId)
    }

    val tabs = listOf("Detail", "Chapters", "Covers")
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    val state = viewModel.state.collectAsState()
    val mangaModel = state.value

    val detailListState = rememberLazyListState()
    val chapterListState = rememberLazyListState()
    val coverListState = rememberLazyListState()

    // --- Collapsing header logic ---
    val density = LocalDensity.current
    val maxHeaderHeightDp = 320.dp
    val maxHeaderHeightPx = with(density) { maxHeaderHeightDp.toPx() }

    var headerOffsetPx by remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Скролл вверх — сначала схлопываем шапку
                if (available.y < 0) {
                    val previous = headerOffsetPx
                    headerOffsetPx = (headerOffsetPx + available.y).coerceIn(-maxHeaderHeightPx, 0f)
                    val consumed = headerOffsetPx - previous
                    return Offset(0f, consumed)
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                // Список долистал до верха, но ещё есть скролл вниз — разворачиваем шапку
                if (available.y > 0) {
                    val previous = headerOffsetPx
                    headerOffsetPx = (headerOffsetPx + available.y).coerceIn(-maxHeaderHeightPx, 0f)
                    return Offset(0f, headerOffsetPx - previous)
                }
                return Offset.Zero
            }
        }
    }

    val headerHeightDp = with(density) {
        (maxHeaderHeightPx + headerOffsetPx).coerceIn(0f, maxHeaderHeightPx).toDp()
    }

    val collapseFraction = (-headerOffsetPx / maxHeaderHeightPx).coerceIn(0f, 1f)
    val topBarColor = lerp(Color.Transparent, MaterialTheme.colorScheme.background, collapseFraction)
    val iconTint = lerp(Color.White, MaterialTheme.colorScheme.onBackground, collapseFraction)
    // --- конец логики шапки ---

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {

            // Шапка: контейнер задаёт видимую высоту (сжимается),
            // внутри — контент фиксированной высоты, обрезается сверху при сжатии
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeightDp)
                    .clipToBounds(),
                contentAlignment = Alignment.BottomCenter
            ) {
                MangaHeader(
                    manga = mangaModel.manga,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(maxHeaderHeightDp)
                )
            }

            // Табы — обычный composable в Column, НЕ внутри LazyColumn.
            // Они всегда идут сразу после шапки, поэтому визуально "прилипают"
            // под топбаром, когда шапка полностью схлопнулась.
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 16.dp,
                divider = {}
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                        text = { Text(text = title, style = MaterialTheme.typography.labelLarge) },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                when (page) {

                    0 -> LazyColumn(
                        state = detailListState,
                        modifier = Modifier.nestedScroll(nestedScrollConnection)
                    ) {
                        item {
                            MangaDetail(
                                state = state.value,
                                onClick = { navController.navigate(NavRoutes.Reader.getPath(it)) },
                                onTagClick = { type, id, title ->
                                    navController.navigate(NavRoutes.MangaFilterList.getPath(type, id, title))
                                },
                                onDetailRetry = { viewModel.getMangaDetail(mangaId) },
                                onRelationsLoadMore = { viewModel.getMangaRelations(mangaId) }
                            )
                        }
                    }

                    1 -> LazyColumn(
                        state = chapterListState,
                        modifier = Modifier.nestedScroll(nestedScrollConnection)
                    ) {
                        items(mangaModel.chapters) { chapter ->
                            ChapterCard(
                                chapter,
                                onClick = { navController.navigate(NavRoutes.Reader.getPath(it)) }
                            )
                        }

                        if (mangaModel.chaptersLoading) {
                            item {
                                Box(Modifier.fillMaxWidth().padding(20.dp), Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        if (mangaModel.chapters.isEmpty()) {
                            item {
                                Box(Modifier.fillMaxWidth().padding(20.dp), Alignment.Center) {
                                    Text("Empty list")
                                }
                            }
                        }

                        mangaModel.chaptersError?.let { error ->
                            item {
                                ErrorBlock(
                                    error = error,
                                    onRetry = { viewModel.getMangaChapters(mangaId) },
                                    modifier = Modifier.padding(16.dp),
                                    content = { Text(text = "Ой, что-то пошло не так...") }
                                )
                            }
                        }
                    }

                    2 -> LazyColumn(
                        state = coverListState,
                        modifier = Modifier.nestedScroll(nestedScrollConnection)
                    ) {
                        item {
                            CoverList(
                                state = mangaModel,
                                onRetry = { viewModel.getMangaCovers(mangaId) }
                            )
                        }
                    }
                }
            }
        }

        // Топбар: back + share, слитно со статус-баром, прозрачный -> сплошной при схлопывании
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(topBarColor)
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(48.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(onBack = { navController.popBackStack() })
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {}) {
                Icon(Icons.Default.Share, null, tint = iconTint)
            }
        }
    }

    LaunchedEffect(chapterListState) {
        snapshotFlow {
            val layoutInfo = chapterListState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@snapshotFlow false
            totalItems > 0 && lastVisible >= totalItems - 5
        }
            .distinctUntilChanged()
            .filter { it }
            .collect { viewModel.getMangaChapters(mangaId) }
    }

    LaunchedEffect(coverListState) {
        snapshotFlow {
            val layoutInfo = coverListState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@snapshotFlow false
            totalItems > 0 && lastVisible >= totalItems - 5
        }
            .distinctUntilChanged()
            .filter { it }
            .collect { viewModel.getMangaCovers(mangaId) }
    }
}



@Composable
fun MangaHeader(
    manga: MangaModel?,
    modifier: Modifier = Modifier
){
        Box(
            modifier = modifier
        ){
                AsyncImage(
                    model = manga?.getCoverUrl(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .blur(25.dp)
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.8f), // верх
                                    Color.Transparent,              // центр
                                    Color.Black.copy(alpha = 0.9f)  // низ
                                )
                            )
                        )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 24.dp,
                            end = 24.dp,
                            top = 72.dp,
                            bottom = 24.dp
                        ),
                    verticalAlignment = Alignment.Bottom
                ) {
                    AsyncImage(
                        model = manga?.getCoverUrl(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(120.dp)
                            .height(180.dp)
                            .clip(RoundedCornerShape(16.dp)),
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        verticalArrangement = Arrangement.Center
                    ){

                        Text(
                            text = manga?.attributes?.altTitles.getPreferredTitle(),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }

        }
}



@Composable
fun MangaDetail(
    state: MangaDetailScreenState,
    onClick: (String) -> Unit,
    onTagClick: (type:String, id:String, title:String) -> Unit,
    onDetailRetry: () -> Unit,
    onRelationsLoadMore: () -> Unit
){
    val manga = state.manga

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        ExpandableDescription(
            text = manga?.attributes?.description.getPreferredDescription()
        )
    }

    TagsSection(
        tags = manga?.attributes?.tags?:emptyList(),
        onClick = onTagClick
    )

    RelationsList(
        state = state,
        onLoadMore = onRelationsLoadMore,
        onClick = onClick
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterCard(
    chapter: MangaVolumeChaptersState,
    onClick:(chapterId:String) -> Unit
){

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showBottomSheet = true }
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = buildString {
                    append("Chapter ")
                    append(chapter.chapter ?: "")
                },
                style = MaterialTheme.typography.titleMedium
            )

            chapter.title?.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "${chapter.translations.size} translations",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null
        )
    }
    if(showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {showBottomSheet = false},
            sheetState = sheetState
        ){
            TranslationBottomSheet(
                chapter.translations,
                onClick = onClick
            )
        }
    }
}


@Composable
fun RelationsList(
    state: MangaDetailScreenState,
    onLoadMore:()-> Unit,
    onClick: (String) -> Unit
){

    var showRelations by remember { mutableStateOf(false) }

    Column(
        modifier=Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
            OutlinedButton(
                onClick = {showRelations = !showRelations}
            ) {
                Text("Relations")
            }


        AnimatedVisibility(visible = showRelations) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(state.relations){relation->
                    RelationCard(
                        relation = relation,
                        onClick = onClick
                    )
                }

                if (state.relationsLoading){

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

                state.relationsError?.let { error->
                    item{
                        ErrorBlock(
                            error = error,
                            onRetry = onLoadMore,
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
    }
}


@Composable
fun RelationCard(
    onClick: (String) -> Unit,
    relation: MangaModel
){
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable(onClick = {onClick(relation.id)})
    ){
        AsyncImage(
            model = relation.getCoverUrl(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = relation.attributes?.altTitles.getPreferredTitle(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


// Убираем coverList как LazyListScope extension, делаем обычный composable
@Composable
fun CoverList(
    state: MangaDetailScreenState,
    onRetry: () -> Unit,
) {
    when {
        state.coversLoading && state.covers.isEmpty() -> {
            Box(Modifier.fillMaxWidth().padding(20.dp), Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.coversError != null && state.covers.isEmpty() -> {
            ErrorBlock(
                error = state.coversError,
                onRetry = onRetry,
                modifier = Modifier.padding(16.dp),
                content = { Text("Ой, что-то пошло не так...") }
            )
        }
        else -> {
            Column {
                state.covers.chunked(2).forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        row.forEach { cover ->
                            Box(Modifier.weight(1f)) { CoverCard(cover) }
                        }
                        if (row.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
                if (state.coversLoading) {
                    Box(Modifier.fillMaxWidth().padding(12.dp), Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}


@Composable
fun CoverCard(cover: MangaVolumeCovers) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = cover.covers?.firstOrNull(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Volume ${cover.volume ?: "unknown"}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


