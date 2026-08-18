package dev.brunofelix.movies.feature.favorite.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.presentation.ui.components.EmptyState
import dev.brunofelix.movies.core.presentation.ui.components.ErrorLayout
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.core.presentation.util.UiText
import dev.brunofelix.movies.feature.favorite.presentation.state.MovieFavoriteState
import dev.brunofelix.movies.feature.favorite.presentation.ui.components.MovieFavoriteList

@Composable
fun MovieFavoriteScreen(
    state: MovieFavoriteState,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    when (state.uiState) {
        is UiState.Loading -> LoadingState()
        is UiState.Success -> {
            MovieFavoriteList(
                paddingValues = paddingValues,
                medias = state.uiState.data,
                onClick = state.onCardClick
            )
        }
        is UiState.Error -> ErrorLayout()
        is UiState.Empty -> EmptyState()
        else -> Unit
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    MovieFavoriteScreen(
        state = MovieFavoriteState()
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    MovieFavoriteScreen(
        state = MovieFavoriteState(
            uiState = UiState.Success(
                data = listOf(
                    Media(id = 1, title = "Movie 1", posterPath = ""),
                    Media(id = 2, title = "Movie 2", posterPath = "")
                )
            )
        )
    )
}

@Preview
@Composable
private fun ErrorPreview() {
    MovieFavoriteScreen(
        state = MovieFavoriteState(
            uiState = UiState.Error(UiText.DynamicString("Error"))
        )
    )
}