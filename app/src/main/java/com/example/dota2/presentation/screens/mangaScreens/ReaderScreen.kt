package com.example.dota2.presentation.screens.mangaScreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.dota2.presentation.screens.components.BackButton
import com.example.dota2.presentation.screens.components.ChapterSettingsSheet
import com.example.dota2.presentation.screens.extensions.shimmerEffect
import com.example.dota2.presentation.uiState.ReadMode
import com.example.dota2.presentation.viewModel.screens.ReaderScreenViewModel


@Composable
fun ReaderScreen(
    onBack:()-> Unit,
    chapterId: String,
    viewModel: ReaderScreenViewModel = hiltViewModel()
){

    val state by viewModel.state.collectAsState()
    var uiVisible by remember { mutableStateOf(false) }

    LaunchedEffect(chapterId){
        viewModel.getChapterServer(chapterId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ){
                uiVisible = !uiVisible
            }
    ) {


        when (state.readMode) {
            ReadMode.Vertical -> VerticalReader(state.pages)
            ReadMode.LeftToRight -> HorizontalReader(state.pages)
            ReadMode.RightToLeft -> HorizontalReader(state.pages.reversed())
        }

        AnimatedVisibility(
            visible = uiVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ReaderTopBar(onBack)
        }

        AnimatedVisibility(
            visible = uiVisible,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ReaderBottomBar(
                currentMode = state.readMode,
                changeMode = viewModel::setReadMode,
                currentPage = state.page,
                totalPages = state.pages.size
            )
        }
    }
}


@Composable
fun ReaderTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background.copy(0.9f)) // фон СНАЧАЛА — закрывает и статус-бар
            .statusBarsPadding()                          // потом отступ под статус-бар
            .padding(12.dp),                               // и уже визуальный padding контента
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(onBack = onBack)
    }
}

@Composable
fun ReaderBottomBar(
    currentMode: ReadMode,
    changeMode: (ReadMode) -> Unit,
    currentPage: Int,
    totalPages: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background.copy(0.9f))
            .navigationBarsPadding()                      // отступ под нав-бар/жест-зону
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${currentPage + 1} / $totalPages",
            color = Color.White
        )

        ChapterSettingsSheet(currentMode, changeMode)
    }
}


@Composable
fun VerticalReader(pages: List<String>) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        items(
            items = pages,
            key = { it } // стабильный key = url страницы, важно для LazyColumn
        ) { page ->
            MangaPage(
                pageUrl = page,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalReader(
    pages: List<String>
) {
    HorizontalPager(
        state = rememberPagerState(
            pageCount = { pages.size }
        )
    ) { page ->

        MangaPage(pageUrl = pages[page])

    }
}


@Composable
fun MangaPage(
    pageUrl: String,
    modifier: Modifier = Modifier,
    fallbackAspectRatio: Float = 0.707f
) {
    SubcomposeAsyncImage(
        model = pageUrl,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
    ) {
        val state = painter.state

        when (state) {
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(fallbackAspectRatio)
                        .shimmerEffect()
                )
            }
            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(fallbackAspectRatio)
                        .background(Color(0xFF1A1A1A)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.BrokenImage,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
            else -> {
                // Плавное появление картинки после загрузки — тоже приятный штрих
                val alpha by animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(300),
                    label = "imageFadeIn"
                )
                SubcomposeAsyncImageContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer { this.alpha = alpha }
                )
            }
        }
    }
}
