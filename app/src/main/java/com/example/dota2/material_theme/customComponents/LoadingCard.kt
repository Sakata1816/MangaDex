package com.example.dota2.material_theme.customComponents

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@Composable
fun LoadingSkeleton(
    modifier: Modifier = Modifier
) {

    val transition = rememberInfiniteTransition(label = "")

    val alpha by transition.animateFloat(
        initialValue = .35f,
        targetValue = .9f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 900,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val shimmer = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        repeat(5) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(10.dp)
            ) {

                Box(
                    modifier = Modifier
                        .size(
                            width = 70.dp,
                            height = 95.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmer)
                )

                Spacer(Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.6f)
                            .height(18.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(shimmer)
                    )

                    Spacer(Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .height(14.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(shimmer)
                    )

                    Spacer(Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.45f)
                            .height(12.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(shimmer)
                    )

                }

            }

        }

    }

}