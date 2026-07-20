package dev.brunofelix.movies.feature.favorite.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.components.ErrorLayout
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.feature.favorite.presentation.state.MovieFavoriteState

@Composable
fun MovieFavoriteContent(
    paddingValues: PaddingValues,
    state: MovieFavoriteState,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        when (state.uiState) {
            is UiState.Loading -> LoadingState()
            is UiState.Success -> {
                MovieFavoriteList(
                    paddingValues = paddingValues,
                    movies = state.uiState.data,
                    onClick = state.onCardClick
                )
            }
            is UiState.Error -> ErrorLayout()
        }
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun LoadingPreview() {
    MovieFavoriteContent(
        state = MovieFavoriteState(),
        paddingValues = PaddingValues(0.dp)
    )
}

@Preview(showBackground = true, name = "Success State")
@Composable
private fun SuccessPreview() {
    val movies = listOf(
        Movie(id = 1, title = "Movie 1", posterPath = ""),
        Movie(id = 2, title = "Movie 2", posterPath = "")
    )
    MovieFavoriteContent(
        state = MovieFavoriteState(
            uiState = UiState.Success(movies)
        ),
        paddingValues = PaddingValues(0.dp)
    )
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun ErrorPreview() {
    MovieFavoriteContent(
        state = MovieFavoriteState(
            uiState = UiState.Error(R.string.error)
        ),
        paddingValues = PaddingValues(0.dp)
    )
}