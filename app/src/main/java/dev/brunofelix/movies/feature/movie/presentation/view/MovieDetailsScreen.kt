package dev.brunofelix.movies.feature.movie.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.presentation.resources.Colors
import dev.brunofelix.movies.feature.movie.presentation.components.details.MovieDetailsBackdropImage
import dev.brunofelix.movies.feature.movie.presentation.components.details.MovieDetailsContent
import dev.brunofelix.movies.feature.movie.presentation.components.details.MovieDetailsTopBar
import dev.brunofelix.movies.feature.movie.presentation.state.MovieDetailsUiState

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
                MovieDetailsBackdropImage(uiState = uiState)
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