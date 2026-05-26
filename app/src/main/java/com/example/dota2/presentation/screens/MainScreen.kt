package com.example.dota2.presentation.screens

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.domain.model.server.TagModel
import com.example.dota2.presentation.screens.components.ErrorBlock
import com.example.dota2.presentation.screens.extensions.getCoverUrl
import com.example.dota2.presentation.screens.extensions.getPreferredTitle
import com.example.dota2.presentation.uiState.MainListUiState
import com.example.dota2.presentation.viewModel.screens.MainScreenViewModel

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
    onLoadMore:()-> Unit
){
    val listState = rememberLazyGridState()


    Box(
        modifier = Modifier.fillMaxSize()
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = listState,
            modifier = Modifier.fillMaxSize()
        ){
            items(state.manga){manga->
                MangaCard(manga, onClick = {})
            }
        }

        if (state.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        state.error?.let {error->
            ErrorBlock(
                error,
                onRetry = onLoadMore,
                modifier = Modifier.align(Alignment.Center),
                content = {
                    Text(
                        text = "Ой, что-то пошло не так...",
                    )
                }
            )
        }

    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { index ->

                val lastIndex = state.manga.lastIndex

                if (index != null && index >= lastIndex - 5) {
                    onLoadMore
                }
            }
    }



}


@Composable
fun MangaCard(
    manga: MangaModel,
    onClick: (String?) -> Unit,
    ){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp,6.dp)
            .clickable { onClick(manga.id) },
        shape = RoundedCornerShape(8.dp),
    ){
        Column(){
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
            ){
                AsyncImage(
                    model = manga.getCoverUrl(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = manga.attributes?.altTitles.getPreferredTitle(),
                fontSize = 16.sp,
            )
        }
    }
}


@Composable
fun TagsCard(
    tag: TagModel,
    onClick:()-> Unit){
    Card(
        modifier = Modifier
            .size(36.dp,18.dp)
            .padding(6.dp,6.dp)
            .clickable{onClick()},
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(3.dp)
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
        ){
            Text(
                text =  tag.attributes?.name?.get("en")?:"TagUiError",
                fontSize = 12.sp,
            )
        }

    }

}

enum class MangaOrder{
    Popular, Rating, LastUpdate
}