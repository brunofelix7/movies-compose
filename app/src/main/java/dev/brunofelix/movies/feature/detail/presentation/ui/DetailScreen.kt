package dev.brunofelix.movies.feature.detail.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.domain.model.MovieGenre
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.components.ErrorLayout
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.feature.detail.presentation.state.MovieDetailState
import dev.brunofelix.movies.feature.detail.presentation.ui.components.DetailContent
import dev.brunofelix.movies.feature.detail.presentation.ui.components.DetailHeader

@Composable
fun DetailScreen(
    state: MovieDetailState,
    modifier: Modifier = Modifier
) {
    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                DetailHeader(state)
            },
            content = { innerPadding ->
                when (state.uiState) {
                    is UiState.Loading -> LoadingState()
                    is UiState.Success -> {
                        DetailContent(
                            state = state,
                            modifier = modifier.padding(innerPadding)
                        )
                    }
                    is UiState.Error -> ErrorLayout()
                }
            }
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    DetailScreen(
        state = MovieDetailState(
            uiState = UiState.Loading,
            isFavorite = false
        )
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    DetailScreen(
        state = MovieDetailState(
            uiState = UiState.Success(MovieUiState(
                genres = listOf(
                    MovieGenre(name = "Action"),
                    MovieGenre(name = "Adventure"),
                    MovieGenre(name = "Comedy"),
                    MovieGenre(name = "Drama"),
                    MovieGenre(name = "Terror")
                )
            )),
            isFavorite = false
        )
    )
}

@Preview
@Composable
private fun ErrorPreview() {
    DetailScreen(
        state = MovieDetailState(
            uiState = UiState.Error(0),
            isFavorite = false
        )
    )
}
