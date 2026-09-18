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

/** Flat black, used by every screen in the app. */
val AppGradient = listOf(Colors.blackPrimary, Colors.blackPrimary)

/** Black bleeding into dark red, reserved for the splash. */
val SplashGradient = listOf(Colors.blackPrimary, Colors.darkRed)

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    colors: List<Color> = AppGradient,
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