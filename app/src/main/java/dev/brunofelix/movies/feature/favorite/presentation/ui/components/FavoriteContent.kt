package dev.brunofelix.movies.feature.favorite.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.ui.components.EmptyState
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.feature.favorite.presentation.state.FavoriteUiState

@Composable
fun FavoriteContent(
    paddingValues: PaddingValues,
    uiState: FavoriteUiState,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        when (uiState.state) {
            is UiState.Success -> {
                FavoriteList(
                    paddingValues = paddingValues,
                    movies = uiState.state.data,
                    onClick = uiState.onCardClick
                )
            }
            is UiState.Error -> {
                EmptyState(
                    message = stringResource(uiState.state.messageRes),
                )
            }
            else -> LoadingState()
        }
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun FavoriteContentPreviewLoading() {
    FavoriteContent(
        uiState = FavoriteUiState(),
        paddingValues = PaddingValues(0.dp)
    )
}

@Preview(showBackground = true, name = "Success State")
@Composable
private fun FavoriteContentPreviewSuccess() {
    val movies = listOf(
        Movie(id = 1, title = "Movie 1", posterPath = ""),
        Movie(id = 2, title = "Movie 2", posterPath = "")
    )
    FavoriteContent(
        uiState = FavoriteUiState(
            state = UiState.Success(movies)
        ),
        paddingValues = PaddingValues(0.dp)
    )
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun FavoriteContentPreviewError() {
    FavoriteContent(
        uiState = FavoriteUiState(
            state = UiState.Error(R.string.error)
        ),
        paddingValues = PaddingValues(0.dp)
    )
}