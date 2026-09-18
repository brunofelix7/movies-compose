package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.presentation.util.UiText

/**
 * Holds the loading, error and empty states of the detail screens.
 *
 * None of those states have media to build a [DetailHeader] from, so they get the back button
 * on its own: the header would otherwise draw its backdrop placeholder and poster over the
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
private fun ErrorPreview() {
    DetailStatusLayout {
        ErrorLayout(errorMessage = UiText.DynamicString("No internet connection"))
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    DetailStatusLayout {
        DetailSkeleton()
    }
}
