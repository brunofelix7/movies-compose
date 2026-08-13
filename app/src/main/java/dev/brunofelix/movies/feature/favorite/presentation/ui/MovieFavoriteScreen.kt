package dev.brunofelix.movies.feature.favorite.presentation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.components.EmptyState
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.core.presentation.util.UiText
import dev.brunofelix.movies.feature.favorite.presentation.state.MovieFavoriteState
import dev.brunofelix.movies.feature.favorite.presentation.ui.components.MovieFavoriteHeader
import dev.brunofelix.movies.feature.favorite.presentation.ui.components.MovieFavoriteList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieFavoriteScreen(
    state: MovieFavoriteState,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    GradientBackground {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            topBar = {
                MovieFavoriteHeader(scrollBehavior)
            },
            content = { innerPadding ->
                when (state.uiState) {
                    is UiState.Loading -> LoadingState()
                    is UiState.Success -> {
                        MovieFavoriteList(
                            paddingValues = innerPadding,
                            movies = state.uiState.data,
                            onClick = state.onCardClick
                        )
                    }
                    is UiState.Error -> EmptyState()
                }
            }
        )
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
                    Movie(id = 1, title = "Movie 1", posterPath = ""),
                    Movie(id = 2, title = "Movie 2", posterPath = "")
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