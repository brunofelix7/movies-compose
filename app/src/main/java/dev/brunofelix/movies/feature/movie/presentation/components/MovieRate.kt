package dev.brunofelix.movies.feature.movie.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.core.presentation.resources.Colors
import dev.brunofelix.movies.core.util.extension.formatDecimal

@Composable
fun MovieRate(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 10.sp,
    rate: Float
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Colors.blackPrimary)
            .padding(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            tint = Color.Yellow,
            contentDescription = "",
            modifier = modifier.size(12.dp)
        )
        Text(
            text = if (rate <= 0) "--" else rate.formatDecimal(),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize
        )
    }
}

@Preview
@Composable
private fun MovieRatePreview() {
    MovieRate(rate = 7.5F)
}