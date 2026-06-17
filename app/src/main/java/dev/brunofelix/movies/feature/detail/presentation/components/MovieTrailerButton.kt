package dev.brunofelix.movies.feature.detail.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun MovieTrailerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(48.dp),
        border = BorderStroke(1.dp, Colors.redPrimary),
        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Colors.redPrimary
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Movie,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Colors.redPrimary
            )
            Text(
                text = "Watch Trailer",
                fontSize = 14.sp,
                color = Colors.redPrimary
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MovieTrailerButton {

    }
}