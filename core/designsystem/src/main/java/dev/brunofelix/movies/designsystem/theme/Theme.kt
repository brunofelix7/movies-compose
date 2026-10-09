package dev.brunofelix.movies.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = RedPrimary,
    onPrimary = White,
    secondary = White,
    onSecondary = BlackPrimary,
    background = BlackPrimary,
    onBackground = White,
    surface = BlackPrimary,
    onSurface = White,
    surfaceVariant = DarkGray,
    onSurfaceVariant = LightGray,
    surfaceContainer = BlackSecondary,
    error = ErrorRed,
    onError = White,
    scrim = BlackPrimary
)

/**
 * The app is dark only: `MyApplication` forces night mode, so there is no light scheme.
 */
@Composable
fun PMovieTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
