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
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.domain.util.extension.toTvShowMediaList
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.components.MediaSection
import dev.brunofelix.movies.presentation.util.ObserveAsEvents
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText

@Composable
internal fun TvShowHomeRoute(
    paddingValues: PaddingValues,
    onNavigateToDetails: (Media) -> Unit,
    onNavigateToMediaList: (MediaListCategory) -> Unit,
    viewModel: TvShowHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is TvShowHomeUiEvent.NavigateToDetails -> onNavigateToDetails(event.media)
            is TvShowHomeUiEvent.NavigateToMediaList -> onNavigateToMediaList(event.category)
        }
    }

    TvShowHomeScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        paddingValues = paddingValues
    )
}

@Composable
internal fun TvShowHomeScreen(
    uiState: TvShowHomeUiState,
    onAction: (TvShowHomeUiAction) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    val onMediaClick = { media: Media -> onAction(TvShowHomeUiAction.OnMediaClick(media)) }
    val onRetry = { onAction(TvShowHomeUiAction.OnRetry) }

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
            onViewMore = { onAction(TvShowHomeUiAction.OnViewMoreClick(MediaListCategory.TV_SHOW_POPULAR)) },
            onRetry = onRetry
        )
        MediaSection(
            title = stringResource(R.string.top_rated),
            state = uiState.topRated,
            onItemClick = onMediaClick,
            onViewMore = { onAction(TvShowHomeUiAction.OnViewMoreClick(MediaListCategory.TV_SHOW_TOP_RATED)) },
            onRetry = onRetry
        )
    }
}

@Composable
private fun PreviewContainer(uiState: TvShowHomeUiState) {
    PMovieTheme {
        GradientBackground {
            TvShowHomeScreen(uiState = uiState, onAction = {})
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    PreviewContainer(TvShowHomeUiState(popular = UiState.Loading, topRated = UiState.Loading))
}

@Preview
@Composable
private fun SuccessPreview() {
    val medias = UiState.Success(TvShow.mocks().toTvShowMediaList())
    PreviewContainer(TvShowHomeUiState(popular = medias, topRated = medias))
}

@Preview
@Composable
private fun EmptyPreview() {
    PreviewContainer(TvShowHomeUiState(popular = UiState.Empty, topRated = UiState.Empty))
}

@Preview
@Composable
private fun ErrorPreview() {
    val error = UiState.Error(UiText.DynamicString("No internet connection"))
    PreviewContainer(TvShowHomeUiState(popular = error, topRated = error))
}
