package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

/**
 * App background. Flat black for now: swap the default [colors] back to
 * `blackPrimary to darkRed` to bring the red gradient back.
 */
@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Colors.blackPrimary, Colors.blackPrimary),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
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