package dev.brunofelix.movies.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Color(0xFF000000), Color(0xFF1D0103)),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = colors
                )
            )
    ) {
        content()
    }
}

@Preview
@Composable
private fun Preview() {
    GradientBackground {

    }
}