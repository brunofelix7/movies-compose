package dev.brunofelix.movies.presentation

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
import dev.brunofelix.movies.designsystem.components.SelectorContentSpacing
import dev.brunofelix.movies.designsystem.components.SelectorTopSpacing
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.spacing24
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.domain.util.extension.toMovieMediaList
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.components.MediaSection
import dev.brunofelix.movies.presentation.util.ObserveAsEvents
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.presentation.components.MonthSelector

private const val PREVIEW_MONTHS_AROUND = 2

@Composable
internal fun ReleaseRoute(
    paddingValues: PaddingValues,
    onNavigateToDetails: (Media) -> Unit,
    onNavigateToMediaList: (MediaListCategory, String) -> Unit,
    viewModel: ReleaseViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is ReleaseUiEvent.NavigateToDetails -> onNavigateToDetails(event.media)
            is ReleaseUiEvent.NavigateToMediaList -> onNavigateToMediaList(event.category, event.monthId)
        }
    }

    ReleaseScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        paddingValues = paddingValues
    )
}

@Composable
internal fun ReleaseScreen(
    uiState: ReleaseUiState,
    onAction: (ReleaseUiAction) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    val onMediaClick = { media: Media -> onAction(ReleaseUiAction.OnMediaClick(media)) }
    val onRetry = { onAction(ReleaseUiAction.OnRetry) }

    Column(
        verticalArrangement = Arrangement.spacedBy(SelectorContentSpacing),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = paddingValues.calculateTopPadding() + SelectorTopSpacing,
                bottom = paddingValues.calculateBottomPadding() + spacing24
            )
    ) {
        MonthSelector(
            months = uiState.months,
            selectedMonth = uiState.selectedMonth,
            onMonthSelected = { onAction(ReleaseUiAction.OnMonthSelected(it)) }
        )

        MediaSection(
            title = stringResource(R.string.releases_theaters),
            state = uiState.theaters,
            onItemClick = onMediaClick,
            onViewMore = { onAction(ReleaseUiAction.OnViewMoreClick(MediaListCategory.RELEASE_THEATERS)) },
            onRetry = onRetry
        )
        MediaSection(
            title = stringResource(R.string.releases_streaming),
            state = uiState.streaming,
            onItemClick = onMediaClick,
            onViewMore = { onAction(ReleaseUiAction.OnViewMoreClick(MediaListCategory.RELEASE_STREAMING)) },
            onRetry = onRetry
        )
        MediaSection(
            title = stringResource(R.string.releases_series),
            state = uiState.series,
            onItemClick = onMediaClick,
            onViewMore = { onAction(ReleaseUiAction.OnViewMoreClick(MediaListCategory.RELEASE_SERIES)) },
            onRetry = onRetry
        )
    }
}

@Composable
private fun PreviewContainer(uiState: ReleaseUiState) {
    PMovieTheme {
        GradientBackground {
            ReleaseScreen(uiState = uiState, onAction = {})
        }
    }
}

private val previewMonths = ReleaseMonth.window(PREVIEW_MONTHS_AROUND, PREVIEW_MONTHS_AROUND)

@Preview
@Composable
private fun LoadingPreview() {
    PreviewContainer(
        ReleaseUiState(
            months = previewMonths,
            theaters = UiState.Loading,
            streaming = UiState.Loading,
            series = UiState.Loading
        )
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    val medias = UiState.Success(Movie.mocks().toMovieMediaList())
    PreviewContainer(ReleaseUiState(months = previewMonths, theaters = medias, streaming = medias, series = medias))
}

@Preview
@Composable
private fun EmptyPreview() {
    PreviewContainer(
        ReleaseUiState(months = previewMonths, theaters = UiState.Empty, streaming = UiState.Empty, series = UiState.Empty)
    )
}

@Preview
@Composable
private fun ErrorPreview() {
    val error = UiState.Error(UiText.DynamicString("No internet connection"))
    PreviewContainer(ReleaseUiState(months = previewMonths, theaters = error, streaming = error, series = error))
}
