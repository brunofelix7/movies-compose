package dev.brunofelix.movies.presentation.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.designsystem.components.DetailSkeleton
import dev.brunofelix.movies.designsystem.components.DetailStatusLayout
import dev.brunofelix.movies.designsystem.components.DetailTopBar
import dev.brunofelix.movies.designsystem.components.EmptyState
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.domain.model.MovieGenre
import dev.brunofelix.movies.presentation.components.ErrorLayout
import dev.brunofelix.movies.presentation.model.MovieUiModel
import dev.brunofelix.movies.presentation.util.ObserveAsEvents
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.presentation.detail.components.MovieDetailContent

private const val BACKDROP_SCREEN_FRACTION = 0.75f
private const val COLLAPSE_RANGE_FRACTION = 0.5f

@Composable
internal fun MovieDetailRoute(
    movieId: Long,
    onBack: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(movieId) {
        viewModel.onAction(MovieDetailUiAction.OnLoad(movieId))
    }

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            MovieDetailUiEvent.NavigateBack -> onBack()
            is MovieDetailUiEvent.ShowToast -> {
                Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    MovieDetailScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun MovieDetailScreen(
    uiState: MovieDetailUiState,
    onAction: (MovieDetailUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val onBack = { onAction(MovieDetailUiAction.OnBack) }

    when (val movieState = uiState.movie) {
        is UiState.Initial -> Unit
        is UiState.Loading -> DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
            DetailSkeleton()
        }
        is UiState.Error -> DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
            ErrorLayout(errorMessage = movieState.uiText, onRetry = { onAction(MovieDetailUiAction.OnRetry) })
        }
        is UiState.Empty -> DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
            EmptyState()
        }
        is UiState.Success -> {
            val movie = movieState.data
            val scrollState = rememberScrollState()
            val imageHeight = LocalConfiguration.current.screenHeightDp.dp * BACKDROP_SCREEN_FRACTION
            val collapseRange = with(LocalDensity.current) { (imageHeight * COLLAPSE_RANGE_FRACTION).toPx() }
            val collapseFraction = if (collapseRange > 0f) {
                (scrollState.value / collapseRange).coerceIn(0f, 1f)
            } else {
                0f
            }

            Scaffold(
                modifier = modifier,
                containerColor = MaterialTheme.colorScheme.background
            ) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize()) {
                    MovieDetailContent(
                        movie = movie,
                        scrollState = scrollState,
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                    )
                    DetailTopBar(
                        title = movie.title,
                        scrollFraction = collapseFraction,
                        isFavorite = uiState.isFavorite,
                        onBackClick = onBack,
                        onFavoriteClick = { onAction(MovieDetailUiAction.OnFavoriteToggle) },
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    PMovieTheme {
        MovieDetailScreen(uiState = MovieDetailUiState(movie = UiState.Loading), onAction = {})
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    PMovieTheme {
        MovieDetailScreen(
            uiState = MovieDetailUiState(
                movie = UiState.Success(
                    MovieUiModel(
                        title = "Super Mario Galaxy",
                        genres = listOf(
                            MovieGenre(name = "Action"),
                            MovieGenre(name = "Adventure"),
                            MovieGenre(name = "Comedy")
                        )
                    )
                ),
                isFavorite = true
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun EmptyPreview() {
    PMovieTheme {
        MovieDetailScreen(uiState = MovieDetailUiState(movie = UiState.Empty), onAction = {})
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    PMovieTheme {
        MovieDetailScreen(
            uiState = MovieDetailUiState(movie = UiState.Error(UiText.DynamicString("Error message"))),
            onAction = {}
        )
    }
}
