package com.example.dota2.material_theme.customComponents

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dota2.domain.model.server.MangaModel
import com.example.dota2.presentation.screens.extensions.getCoverUrl
import com.example.dota2.presentation.screens.extensions.getPreferredTitle


@Composable
fun MangaCard(
    manga: MangaModel,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick(manga.id) }
            .animateContentSize()
    ) {

        Box {

            AsyncImage(
                model = manga.getCoverUrl(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.68f)
                    .clip(RoundedCornerShape(14.dp)),
                contentScale = ContentScale.Crop
            )

            manga.attributes?.status?.let { status ->

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    color = MaterialTheme.colorScheme.surface.copy(.9f),
                    shape = RoundedCornerShape(8.dp)
                ) {

                    Text(
                        text = status.uppercase(),
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 3.dp
                        ),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                }

            }

        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = manga.attributes?.altTitles.getPreferredTitle(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            manga.attributes?.year?.let {

                Text(
                    text = it.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }

            if (manga.type != null) {

                Spacer(Modifier.width(6.dp))

                Text(
                    "•",
                    color = MaterialTheme.colorScheme.outline
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    manga.type.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }

        }

    }

}