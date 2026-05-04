package dev.brunofelix.movies.feature.favorite.presentation.components

import androidx.compose.foundation.background
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
import dev.brunofelix.movies.core.presentation.components.EmptyState
import dev.brunofelix.movies.core.presentation.components.LoadingState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    uiState: UiState<List<Movie>>,
    onClick: (id: Long) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(Colors.blackPrimary)
            .fillMaxSize()
    ) {
        when (uiState) {
            is UiState.Loading -> LoadingState()
            is UiState.Success -> {
                FavoriteList(
                    paddingValues = paddingValues,
                    movies = uiState.data,
                    onClick = onClick
                )
            }
            is UiState.Empty -> {
                EmptyState(
                    message = stringResource(R.string.empty_state),
                )
            }
            is UiState.Error -> {
                EmptyState(
                    message = stringResource(uiState.messageRes),
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun FavoriteContentPreviewLoading() {
    FavoriteContent(
        uiState = UiState.Loading,
        paddingValues = PaddingValues(0.dp),
        onClick = { }
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
        uiState = UiState.Success(movies),
        paddingValues = PaddingValues(0.dp),
        onClick = {}
    )
}

@Preview(showBackground = true, name = "Empty State")
@Composable
private fun FavoriteContentPreviewEmpty() {
    FavoriteContent(
        uiState = UiState.Empty,
        paddingValues = PaddingValues(0.dp),
        onClick = {}
    )
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun FavoriteContentPreviewError() {
    FavoriteContent(
        uiState = UiState.Error(R.string.error),
        paddingValues = PaddingValues(0.dp),
        onClick = {}
    )
}