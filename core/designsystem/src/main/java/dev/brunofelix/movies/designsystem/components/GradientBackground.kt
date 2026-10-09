package dev.brunofelix.movies.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.theme.BlackPrimary
import dev.brunofelix.movies.designsystem.theme.DarkRed
import dev.brunofelix.movies.designsystem.theme.PMovieTheme

/** Flat black, used by every screen in the app. */
val AppGradient = listOf(BlackPrimary, BlackPrimary)

/** Black bleeding into dark red, reserved for the splash. */
val SplashGradient = listOf(BlackPrimary, DarkRed)

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
    PMovieTheme {
        GradientBackground {}
    }
}

@Preview
@Composable
private fun SplashPreview() {
    PMovieTheme {
        GradientBackground(colors = SplashGradient) {}
    }
}
