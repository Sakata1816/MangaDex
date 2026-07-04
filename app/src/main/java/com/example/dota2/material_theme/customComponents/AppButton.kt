package com.example.dota2.material_theme.customComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp





@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: AppButtonStyle = AppButtonStyle.Filled,
    icon: ImageVector? = null
) {

    val containerColor = when (style) {
        AppButtonStyle.Filled -> MaterialTheme.colorScheme.primary
        AppButtonStyle.Outlined -> Color.Transparent
        AppButtonStyle.Text -> Color.Transparent
    }

    val contentColor = when (style) {
        AppButtonStyle.Filled -> MaterialTheme.colorScheme.onPrimary
        AppButtonStyle.Outlined -> MaterialTheme.colorScheme.primary
        AppButtonStyle.Text -> MaterialTheme.colorScheme.primary
    }

    Surface(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        color = containerColor,
        border = if (style == AppButtonStyle.Outlined)
            BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline
            ) else null
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            icon?.let {

                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = contentColor
                )

                Spacer(Modifier.width(8.dp))
            }

            Text(
                text = text,
                color = contentColor,
                style = MaterialTheme.typography.labelLarge
            )

        }

    }

}

enum class AppButtonStyle {
    Filled,
    Outlined,
    Text
}