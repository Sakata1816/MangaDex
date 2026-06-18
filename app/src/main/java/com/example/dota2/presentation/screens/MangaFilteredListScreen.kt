package com.example.dota2.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.state.MangaFilters
import com.example.dota2.presentation.navigation.mainRoot.NavRoutes
import com.example.dota2.presentation.screens.components.ErrorBlock
import com.example.dota2.presentation.screens.extensions.getCoverUrl
import com.example.dota2.presentation.screens.extensions.getPreferredTitle
import com.example.dota2.presentation.viewModel.screens.MangaFilteredListViewModel
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun MangaFilterList(
    type: String,
    typeId: String,
    title: String,
    viewModel: MangaFilteredListViewModel = hiltViewModel(),
    navController: NavController
){

    val filter = when(type){
        "tag"-> MangaFilters(includedTags = listOf(typeId))
        "author"-> MangaFilters(author = listOf(typeId))
        "artist"-> MangaFilters(artist = listOf(typeId))
        else -> MangaFilters()
    }

    LaunchedEffect(typeId) {
        viewModel.loadMangaFilteredList(filter)
    }


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
            Text(
                title,
                fontSize = 24.sp,
            )
        }

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
                        onRetry = {viewModel.loadMangaFilteredList(filter)},
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
                    viewModel.loadMangaFilteredList(filter)
                }
            }
    }

}


@Composable
fun MangaFilterCard(
    manga: MangaModel,
    onClick: (String) -> Unit,
) {
    val statusColor = when (manga.attributes?.status?.lowercase()) {
        "ongoing" -> Color(0xFF4CAF50)
        "completed" -> Color(0xFF2196F3)
        "hiatus" -> Color(0xFFFF9800)
        "cancelled" -> Color(0xFFF44336)
        else -> Color(0xFF9E9E9E)
    }

    val demographic = manga.attributes?.publicationDemographic
        ?.replaceFirstChar { it.uppercase() }

    val rating = manga.attributes?.contentRating
        ?.replaceFirstChar { it.uppercase() }

    val tags = manga.attributes?.tags
        ?.take(3)
        ?.mapNotNull { it.attributes?.name?.get("en") }
        ?: emptyList()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .clickable { onClick(manga.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {

            // Обложка с цветным статус-индикатором слева
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
            ) {
                AsyncImage(
                    model = manga.getCoverUrl(),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )

                // Статус-полоска снизу обложки
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(statusColor)
                        .align(Alignment.BottomCenter)
                )
            }

            // Контент
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                // Заголовок
                Text(
                    text = manga.attributes?.altTitles.getPreferredTitle(),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )

                // Строка: статус + год
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = statusColor.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = manga.attributes?.status
                                ?.replaceFirstChar { it.uppercase() } ?: "Unknown",
                            style = MaterialTheme.typography.labelSmall,
                            color = statusColor,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    manga.attributes?.year?.let {
                        Text(
                            text = it.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    demographic?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Теги
                if (tags.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        tags.forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = tag,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // Главы + язык оригинала
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    manga.attributes?.lastChapter?.let {
                        Text(
                            text = "Ch. $it",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    manga.attributes?.originalLanguage?.let {
                        Text(
                            text = it.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}