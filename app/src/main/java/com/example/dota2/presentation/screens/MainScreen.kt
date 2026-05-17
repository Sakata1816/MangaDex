package com.example.dota2.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.presentation.viewModel.screens.MainScreenViewModel

@Composable
fun MainScreen(
    modifier: Modifier,
    viewModel: MainScreenViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()

    val listState = rememberLazyListState()


    LazyColumn(state = listState, modifier = modifier) {
        items(state.manga){manga->

            MangaCard(manga = manga)
            Divider()

        }

    }

}


@Composable
fun MangaCard(manga: MangaModel) {
    val title = manga.attributes?.title?.get("en")
        ?: manga.attributes?.title?.values?.firstOrNull()
        ?: "Unknown"

    Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = manga.getCoverUrl(),
            contentDescription = null,
            modifier = Modifier
                .width(64.dp)
                .height(90.dp)
        )
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(text = title, fontSize = 16.sp)
            Text(text = manga.attributes?.year?.toString() ?: "", fontSize = 12.sp)
            Text(text = manga.attributes?.status ?: "", fontSize = 12.sp)
        }
    }
}

fun MangaModel.getCoverUrl(): String? {
    val fileName = relationships
        ?.firstOrNull { it.type == "cover_art" }
        ?.attributes
        ?.get("fileName") as? String

    return if (id != null && fileName != null) {
        "https://uploads.mangadex.org/covers/$id/$fileName"
    } else null
}