package dev.brunofelix.movies.feature.movie.detail.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.model.MovieGenre
import dev.brunofelix.movies.core.presentation.ui.components.DetailHeader
import dev.brunofelix.movies.core.presentation.ui.components.DetailSkeleton
import dev.brunofelix.movies.core.presentation.ui.components.DetailStatusLayout
import dev.brunofelix.movies.core.presentation.ui.components.EmptyState
import dev.brunofelix.movies.core.presentation.ui.components.ErrorLayout
import dev.brunofelix.movies.core.presentation.ui.model.MovieUiModel
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.core.presentation.util.UiText
import dev.brunofelix.movies.feature.movie.detail.presentation.ui.components.MovieDetailContent

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
        onFavorite = { viewModel.onFavoriteToggle() }
    )
}

@Composable
private fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<MovieUiModel>,
    isFavorite: Boolean,
    onBack: () -> Unit = {},
    onFavorite: () -> Unit = {}
) {
    when (uiState) {
        is UiState.Initial -> Unit
        is UiState.Loading -> {
            DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
                DetailSkeleton()
            }
        }
        is UiState.Error -> {
            DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
                ErrorLayout(errorMessage = uiState.uiText)
            }
        }
        is UiState.Empty -> {
            DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
                EmptyState()
            }
        }
        is UiState.Success -> {
            val movie = uiState.data

            Scaffold(
                modifier = modifier,
                containerColor = Colors.blackPrimary,
                topBar = {
                    DetailHeader(
                        backdropPath = movie.backdropPath,
                        media = Media(
                            id = movie.id,
                            title = movie.title,
                            posterPath = movie.posterPath,
                            releaseDate = movie.releaseDate
                        ),
                        isFavorite = isFavorite,
                        onBackClick = onBack,
                        onFavoriteClick = onFavorite
                    )
                },
                content = { innerPadding ->
                    MovieDetailContent(
                        movie = movie,
                        modifier = Modifier.padding(innerPadding)
                    )
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
            title = "Super Mario Galaxy",
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
