package dev.brunofelix.movies.feature.detail.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.brunofelix.movies.core.domain.model.MovieGenre
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.components.ErrorLayout
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.core.presentation.util.extension.sharedViewModel
import dev.brunofelix.movies.feature.detail.presentation.state.MovieDetailState
import dev.brunofelix.movies.feature.detail.presentation.ui.components.DetailContent
import dev.brunofelix.movies.feature.detail.presentation.ui.components.DetailHeader
import dev.brunofelix.movies.feature.detail.presentation.viewmodel.DetailViewModel

@Composable
fun MovieDetailRoute(
    movieId: Long,
    navController: NavController,
    backStackEntry: NavBackStackEntry
) {
    // 1. Get the ViewModel from the NavBackStackEntry
    val movieDetailViewModel = backStackEntry.sharedViewModel<DetailViewModel>(navController)

    // 2. Collect reactive states
    val uiState by movieDetailViewModel.uiState.collectAsStateWithLifecycle()
    val isFavorite by movieDetailViewModel.isFavorite.collectAsStateWithLifecycle()

    // 3. Memos the lambdas to have identical references in memory
    val onBack: () -> Unit = remember { { navController.popBackStack() } }
    val onFavorite: () -> Unit = remember { { movieDetailViewModel.onFavoriteToggle() } }
    val onWatchTrailer: () -> Unit = remember { { /* call trailer logic */ } }

    // 4. Instantiates your mandatory state class
    val state = MovieDetailState(
        uiState = uiState,
        isFavorite = isFavorite,
        onBack = onBack,
        onFavorite = onFavorite,
        onWatchTrailer = onWatchTrailer
    )

    // 5. Triggers the API only when the movie ID actually changes
    LaunchedEffect(movieId) {
        movieDetailViewModel.getDetails(movieId)
    }

    // 6. Sends the unified state to the pure screen
    MovieDetailScreen(state)
}

@Composable
private fun MovieDetailScreen(
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
            uiState = UiState.Error(0),
            isFavorite = false
        )
    )
}
