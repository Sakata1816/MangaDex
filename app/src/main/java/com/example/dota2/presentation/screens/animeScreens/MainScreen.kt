package com.example.dota2.presentation.screens.animeScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.model.server.TagModel
import com.example.dota2.presentation.navigation.mainRoot.NavRoutes
import com.example.dota2.presentation.screens.components.ErrorBlock
import com.example.dota2.presentation.screens.extensions.getCoverUrl
import com.example.dota2.presentation.screens.extensions.getPreferredTitle
import com.example.dota2.presentation.uiState.MainListUiState
import com.example.dota2.presentation.viewModel.screens.MainScreenViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
    ){

    val popularState by viewModel.popularState.collectAsState()
    val ratingState by viewModel.ratingState.collectAsState()
    val lastUpdateState by viewModel.lastUpdateState.collectAsState()
    val tagsState by viewModel.tagsState.collectAsState()

    var currentState by remember { mutableStateOf(MangaOrder.Popular) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                Row() {
                    MangaOrder.entries.forEach { order->
                        Button(
                            onClick = { currentState = order },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (currentState == order)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Text(order.name)
                        }
                    }
                }
            }


            val activeState = when(currentState){
                MangaOrder.Popular -> popularState
                MangaOrder.Rating -> ratingState
                MangaOrder.LastUpdate -> lastUpdateState
            }

            MangaList(
                state = activeState,
                onClick = {id->
                    navController.navigate(NavRoutes.MangaDetail.getPath(id))
                },
                onLoadMore = {
                    when(currentState){
                        MangaOrder.Popular -> viewModel.loadListByPopular()
                        MangaOrder.Rating -> viewModel.loadListByRating()
                        MangaOrder.LastUpdate -> viewModel.loadListByLastUpdate()
                    }
                }
            )
        }
}







@Composable
fun MangaList(
    state: MainListUiState,
    onClick: (String) -> Unit,
    onLoadMore:()-> Unit,
){
    val listState = rememberLazyGridState()


        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                top = 8.dp,
                bottom = 80.dp
            )
        ){
            items(state.manga){manga->
                MangaCard(manga, onClick = onClick)
            }


    if (state.isLoading) {

        item(
            span = {
                GridItemSpan(maxLineSpan)
            }
        ) {

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
            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ){
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
}


@Composable
fun MangaCard(
    manga: MangaModel,
    onClick: (String) -> Unit,
    ){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {onClick(manga.id)})
        ){
            AsyncImage(
                model = manga.getCoverUrl(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = manga.attributes?.altTitles.getPreferredTitle(),
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 2.dp),
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp
            )
        }

}


@Composable
fun TagsCard(
    tag: TagModel,
    onClick:(type:String, id:String, title:String)-> Unit){
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .clickable{onClick("tag", tag.id?:"", tag.attributes?.name?.get("en")?:"Unknown")},
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ){
            Text(
                text =  tag.attributes?.name?.get("en")?:"Unknown",
                fontSize = 12.sp,
            )
        }
    }
}

enum class MangaOrder{
    Popular, Rating, LastUpdate
}