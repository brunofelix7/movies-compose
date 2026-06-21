package dev.brunofelix.movies.feature.detail.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.presentation.components.MovieCard
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.resources.Colors
import dev.brunofelix.movies.feature.detail.presentation.components.DetailTopBar
import dev.brunofelix.movies.feature.detail.presentation.components.MovieDetailsContent
import dev.brunofelix.movies.feature.detail.presentation.components.MovieDetailsTopBarImage

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<MovieUiState>,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Scaffold(
        topBar = {
            Box {
                MovieDetailsTopBarImage(uiState = uiState)
                DetailTopBar(
                    isFavorite = isFavorite,
                    onBackClick = onBackClick,
                    onFavoriteClick = onFavoriteClick
                )
                (uiState as? UiState.Success)?.let {
                    MovieCard(
                        uiState = it.data,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth(0.50F)
                            .align(Alignment.BottomStart)
                            .offset(y = 100.dp)
                    )
                }
            }
        },
        content = { innerPadding ->
            MovieDetailsContent(
                modifier = modifier.padding(innerPadding),
                uiState = uiState
            )
        },
        containerColor = Colors.blackPrimary,
        contentColor = Colors.white
    )
}

@Preview
@Composable
private fun Preview() {
    MovieDetailsScreen(
        uiState = UiState.Success(MovieUiState()),
        isFavorite = false,
        onBackClick = {},
        onFavoriteClick = {}
    )
}