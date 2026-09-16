package dev.brunofelix.movies.feature.release.presentation.ui

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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.core.presentation.ui.components.MediaSection
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme
import dev.brunofelix.movies.feature.release.presentation.state.ReleaseState
import dev.brunofelix.movies.feature.release.presentation.ui.components.MonthSelector
import dev.brunofelix.movies.feature.release.presentation.viewmodel.ReleaseViewModel

@Composable
fun ReleaseScreen(
    onItemClick: (Media) -> Unit,
    onViewMore: (MediaListCategory, ReleaseMonth) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: ReleaseViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ReleaseContent(
        state = state,
        paddingValues = paddingValues,
        onMonthSelected = viewModel::onMonthSelected,
        onItemClick = onItemClick,
        onViewMore = onViewMore,
        onRetry = viewModel::onRetry,
        modifier = modifier
    )
}

@Composable
private fun ReleaseContent(
    state: ReleaseState,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onMonthSelected: (ReleaseMonth) -> Unit = {},
    onItemClick: (Media) -> Unit = {},
    onViewMore: (MediaListCategory, ReleaseMonth) -> Unit = { _, _ -> },
    onRetry: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                top = paddingValues.calculateTopPadding() + 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 24.dp
            )
    ) {
        MonthSelector(
            months = state.months,
            selectedMonth = state.selectedMonth,
            onMonthSelected = onMonthSelected
        )

        MediaSection(
            title = stringResource(R.string.releases_theaters),
            state = state.theaters,
            onItemClick = onItemClick,
            onViewMore = {
                onViewMore(MediaListCategory.RELEASE_THEATERS, state.selectedMonth)
            },
            onRetry = onRetry
        )
        MediaSection(
            title = stringResource(R.string.releases_streaming),
            state = state.streaming,
            onItemClick = onItemClick,
            onViewMore = {
                onViewMore(MediaListCategory.RELEASE_STREAMING, state.selectedMonth)
            },
            onRetry = onRetry
        )
        MediaSection(
            title = stringResource(R.string.releases_series),
            state = state.series,
            onItemClick = onItemClick,
            onViewMore = {
                onViewMore(MediaListCategory.RELEASE_SERIES, state.selectedMonth)
            },
            onRetry = onRetry
        )
    }
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        ReleaseContent(
            state = ReleaseState(months = ReleaseMonth.window(2, 2))
        )
    }
}
