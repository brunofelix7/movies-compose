package dev.brunofelix.movies.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.theme.PMovieTheme

/**
 * Holds the loading, error and empty states of the detail screens.
 *
 * None of those states have media to show, so they only get the back button on top of the
 * state underneath it.
 */
@Composable
fun DetailStatusLayout(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()
        DetailTopBar(
            isFavorite = false,
            shouldShowFavorite = false,
            onBackClick = onBackClick
        )
    }
}

@Preview
@Composable
private fun EmptyPreview() {
    PMovieTheme {
        DetailStatusLayout {
            EmptyState()
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    PMovieTheme {
        DetailStatusLayout {
            DetailSkeleton()
        }
    }
}
