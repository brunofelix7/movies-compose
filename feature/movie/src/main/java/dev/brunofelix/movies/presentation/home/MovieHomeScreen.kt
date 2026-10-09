package dev.brunofelix.movies.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.designsystem.components.GradientBackground
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing24
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.domain.util.extension.toMovieMediaList
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.components.MediaSection
import dev.brunofelix.movies.presentation.util.ObserveAsEvents
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText

@Composable
internal fun MovieHomeRoute(
    paddingValues: PaddingValues,
    onNavigateToDetails: (Media) -> Unit,
    onNavigateToMediaList: (MediaListCategory) -> Unit,
    viewModel: MovieHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is MovieHomeUiEvent.NavigateToDetails -> onNavigateToDetails(event.media)
            is MovieHomeUiEvent.NavigateToMediaList -> onNavigateToMediaList(event.category)
        }
    }

    MovieHomeScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        paddingValues = paddingValues
    )
}

@Composable
internal fun MovieHomeScreen(
    uiState: MovieHomeUiState,
    onAction: (MovieHomeUiAction) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    val onMediaClick = { media: Media -> onAction(MovieHomeUiAction.OnMediaClick(media)) }
    val onRetry = { onAction(MovieHomeUiAction.OnRetry) }

    Column(
        verticalArrangement = Arrangement.spacedBy(spacing24),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = paddingValues.calculateTopPadding() + spacing16,
                bottom = paddingValues.calculateBottomPadding() + spacing24
            )
    ) {
        MediaSection(
            title = stringResource(R.string.popular),
            state = uiState.popular,
            onItemClick = onMediaClick,
            onViewMore = { onAction(MovieHomeUiAction.OnViewMoreClick(MediaListCategory.MOVIE_POPULAR)) },
            onRetry = onRetry
        )
        MediaSection(
            title = stringResource(R.string.upcoming),
            state = uiState.upcoming,
            onItemClick = onMediaClick,
            onViewMore = { onAction(MovieHomeUiAction.OnViewMoreClick(MediaListCategory.MOVIE_UPCOMING)) },
            onRetry = onRetry
        )
        MediaSection(
            title = stringResource(R.string.top_rated),
            state = uiState.topRated,
            onItemClick = onMediaClick,
            onViewMore = { onAction(MovieHomeUiAction.OnViewMoreClick(MediaListCategory.MOVIE_TOP_RATED)) },
            onRetry = onRetry
        )
    }
}

@Composable
private fun PreviewContainer(uiState: MovieHomeUiState) {
    PMovieTheme {
        GradientBackground {
            MovieHomeScreen(uiState = uiState, onAction = {})
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    PreviewContainer(
        MovieHomeUiState(popular = UiState.Loading, upcoming = UiState.Loading, topRated = UiState.Loading)
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    val medias = UiState.Success(Movie.mocks().toMovieMediaList())
    PreviewContainer(MovieHomeUiState(popular = medias, upcoming = medias, topRated = medias))
}

@Preview
@Composable
private fun EmptyPreview() {
    PreviewContainer(MovieHomeUiState(popular = UiState.Empty, upcoming = UiState.Empty, topRated = UiState.Empty))
}

@Preview
@Composable
private fun ErrorPreview() {
    val error = UiState.Error(UiText.DynamicString("No internet connection"))
    PreviewContainer(MovieHomeUiState(popular = error, upcoming = error, topRated = error))
}
