package dev.brunofelix.movies.feature.movie.detail.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.core.domain.model.MovieGenre
import dev.brunofelix.movies.core.presentation.navigation.Navigator
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.components.ErrorLayout
import dev.brunofelix.movies.core.presentation.util.UiText
import dev.brunofelix.movies.feature.movie.detail.presentation.state.MovieDetailState
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.components.MovieDetailContent
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.components.MovieDetailHeader
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.components.MovieDetailSkeleton
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.components.MovieDetailTopBar
import dev.brunofelix.movies.feature.movie.detail.presentation.viewmodel.MovieDetailViewModel

@Composable
fun MovieDetailRoute(
    movieId: Long,
    navigator: Navigator,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    // Collect reactive states
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()

    // Memos the lambdas to have identical references in memory
    val onBack: () -> Unit = remember { { navigator.goBack() } }
    val onFavorite: () -> Unit = remember { { viewModel.onFavoriteToggle() } }
    val onWatchTrailer: () -> Unit = remember { { /* call trailer logic */ } }

    // Instantiates your mandatory state class
    val state = MovieDetailState(
        uiState = uiState,
        isFavorite = isFavorite,
        onBack = onBack,
        onFavorite = onFavorite,
        onWatchTrailer = onWatchTrailer
    )

    // Triggers the API only when the movie ID actually changes
    LaunchedEffect(movieId) {
        viewModel.getDetails(movieId)
    }

    // Sends the unified state to the pure screen
    MovieDetailScreen(state)
}

@Composable
private fun MovieDetailScreen(
    state: MovieDetailState,
    modifier: Modifier = Modifier
) {
    if (state.uiState is UiState.Loading) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            MovieDetailSkeleton()
            MovieDetailTopBar(
                isFavorite = false,
                shouldShowFavorite = false,
                onBackClick = state.onBack,
                onFavoriteClick = {}
            )
        }
    } else {
        Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent,
            topBar = {
                MovieDetailHeader(state)
            },
            content = { innerPadding ->
                when (state.uiState) {
                    is UiState.Success -> {
                        MovieDetailContent(
                            state = state,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    is UiState.Error -> {
                        ErrorLayout(errorMessage = state.uiState.uiText)
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    MovieDetailScreen(
        state = MovieDetailState(
            uiState = UiState.Loading,
            isFavorite = false
        )
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    MovieDetailScreen(
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
    MovieDetailScreen(
        state = MovieDetailState(
            uiState = UiState.Error(UiText.DynamicString("Error message")),
            isFavorite = false
        )
    )
}
