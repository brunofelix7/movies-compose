package dev.brunofelix.movies.feature.detail.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun MovieInfoChip(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconTint: Color = Colors.redPrimary,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            tint = iconTint,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Colors.lightGray,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MovieInfoChip(
        icon = Icons.Outlined.CalendarMonth,
        text = "01/04/2026"
    )
}