package dev.brunofelix.movies.feature.movie.detail.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.core.domain.model.MovieGenre
import dev.brunofelix.movies.core.presentation.ui.components.EmptyState
import dev.brunofelix.movies.core.presentation.ui.components.ErrorLayout
import dev.brunofelix.movies.core.presentation.ui.model.MovieUiModel
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.core.presentation.util.UiText
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.components.MovieDetailContent
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.components.MovieDetailHeader
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.components.MovieDetailSkeleton
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.components.MovieDetailTopBar

@Composable
fun MovieDetailRoute(
    movieId: Long,
    onBack: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.getDetails(movieId)
    }

    MovieDetailScreen(
        uiState = uiState,
        isFavorite = isFavorite,
        onBack = onBack,
        onFavorite = { viewModel.onFavoriteToggle() },
        onWatchTrailer = {
            // TODO: call watch trailer logic
        }
    )
}

@Composable
private fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<MovieUiModel>,
    isFavorite: Boolean,
    onBack: () -> Unit = {},
    onFavorite: () -> Unit = {},
    onWatchTrailer: () -> Unit = {}
) {
    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                MovieDetailSkeleton()
                MovieDetailTopBar(
                    isFavorite = false,
                    shouldShowFavorite = false,
                    onBackClick = onBack
                )
            }
        }
        else -> {
            Scaffold(
                modifier = modifier,
                containerColor = Color.Transparent,
                topBar = {
                    MovieDetailHeader(
                        movie = (uiState as? UiState.Success)?.data,
                        isFavorite = isFavorite,
                        onBackClick = onBack,
                        onFavoriteClick = onFavorite
                    )
                },
                content = { innerPadding ->
                    when (uiState) {
                        is UiState.Success -> {
                            MovieDetailContent(
                                movie = uiState.data,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        is UiState.Error -> {
                            ErrorLayout(errorMessage = uiState.uiText)
                        }
                        is UiState.Empty -> EmptyState()
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    MovieDetailScreen(
        uiState = UiState.Loading,
        isFavorite = false
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    MovieDetailScreen(
        uiState = UiState.Success(MovieUiModel(
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
}

@Preview
@Composable
private fun ErrorPreview() {
    MovieDetailScreen(
        uiState = UiState.Error(UiText.DynamicString("Error message")),
        isFavorite = false
    )
}
