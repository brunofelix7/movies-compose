package dev.brunofelix.movies.feature.details.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.presentation.ui.resources.Colors
import dev.brunofelix.movies.feature.details.presentation.ui.components.MovieDetailsContent
import dev.brunofelix.movies.feature.details.presentation.ui.components.MovieDetailsTopBar
import dev.brunofelix.movies.feature.details.presentation.ui.components.MovieDetailsTopBarImage
import dev.brunofelix.movies.feature.details.presentation.viewmodel.state.MovieDetailsUiState

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: MovieDetailsUiState,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val isFavorite = (uiState as? MovieDetailsUiState.Success)?.isFavorite ?: false

    Scaffold(
        topBar = {
            Box {
                MovieDetailsTopBarImage(uiState = uiState)
                MovieDetailsTopBar(
                    isFavorite = isFavorite,
                    onBackClick = onBackClick,
                    onFavoriteClick = onFavoriteClick
                )
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
private fun MovieDetailsScreenPreview() {
    MovieDetailsScreen(
        uiState = MovieDetailsUiState.Initial,
        onBackClick = {  },
        onFavoriteClick = {  }
    )
}