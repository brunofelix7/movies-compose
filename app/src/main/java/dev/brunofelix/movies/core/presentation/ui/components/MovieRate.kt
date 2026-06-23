package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.core.presentation.ui.resources.Colors
import dev.brunofelix.movies.core.util.extension.formatDecimal

@Composable
fun MovieRate(
    rate: Float,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .size(width = 68.dp, height = 35.dp)
            .clip(RoundedCornerShape(32.dp))
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Colors.white.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(32.dp)
            )
            .background(color = Colors.blackPrimary.copy(alpha = 0.6f))
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            tint = Color.Yellow,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.size(4.dp))
        Text(
            text = if (rate <= 0) "--" else rate.formatDecimal(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MovieRate(7.5F)
}