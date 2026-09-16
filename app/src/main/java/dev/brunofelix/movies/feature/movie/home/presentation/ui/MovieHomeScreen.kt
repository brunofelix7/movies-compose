package dev.brunofelix.movies.feature.movie.home.presentation.ui

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
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.core.presentation.ui.components.MediaSection
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme
import dev.brunofelix.movies.feature.movie.home.presentation.state.MovieHomeState
import dev.brunofelix.movies.feature.movie.home.presentation.viewmodel.MovieHomeViewModel

@Composable
fun MovieHomeScreen(
    onItemClick: (Media) -> Unit,
    onViewMore: (MediaListCategory) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: MovieHomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MovieHomeContent(
        state = state,
        paddingValues = paddingValues,
        onItemClick = onItemClick,
        onViewMore = onViewMore,
        onRetry = viewModel::onRetry,
        modifier = modifier
    )
}

@Composable
private fun MovieHomeContent(
    state: MovieHomeState,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onItemClick: (Media) -> Unit = {},
    onViewMore: (MediaListCategory) -> Unit = {},
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
        // The category selector moves to a filter action later on.
        //
        // CategorySelector(
        //     categories = MovieCategory.entries,
        //     selectedCategory = selectedCategory,
        //     onCategorySelected = viewModel::onCategorySelected
        // )

        MediaSection(
            title = stringResource(R.string.popular),
            state = state.popular,
            onItemClick = onItemClick,
            onViewMore = { onViewMore(MediaListCategory.MOVIE_POPULAR) },
            onRetry = onRetry
        )
        MediaSection(
            title = stringResource(R.string.upcoming),
            state = state.upcoming,
            onItemClick = onItemClick,
            onViewMore = { onViewMore(MediaListCategory.MOVIE_UPCOMING) },
            onRetry = onRetry
        )
        MediaSection(
            title = stringResource(R.string.top_rated),
            state = state.topRated,
            onItemClick = onItemClick,
            onViewMore = { onViewMore(MediaListCategory.MOVIE_TOP_RATED) },
            onRetry = onRetry
        )
    }
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        MovieHomeContent(state = MovieHomeState())
    }
}
