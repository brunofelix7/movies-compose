package dev.brunofelix.movies.feature.tv_show.detail.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import dev.brunofelix.movies.core.presentation.ui.model.TvShowUiModel
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.core.presentation.util.UiText
import dev.brunofelix.movies.feature.tv_show.detail.presentation.ui.components.TvShowDetailContent
import dev.brunofelix.movies.feature.tv_show.detail.presentation.ui.components.TvShowDetailHeader
import dev.brunofelix.movies.feature.tv_show.detail.presentation.ui.components.TvShowDetailSkeleton
import dev.brunofelix.movies.feature.tv_show.detail.presentation.ui.components.TvShowDetailTopBar

@Composable
fun TvShowDetailRoute(
    tvShowId: Long,
    onBack: () -> Unit,
    viewModel: TvShowDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(tvShowId) {
        viewModel.getDetails(tvShowId)
    }

    TvShowDetailScreen(
        uiState = uiState,
        isFavorite = isFavorite,
        scrollState = scrollState,
        onBack = onBack,
        onFavorite = { viewModel.onFavoriteToggle() }
    )
}

@Composable
private fun TvShowDetailScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<TvShowUiModel>,
    isFavorite: Boolean,
    scrollState: androidx.compose.foundation.ScrollState = rememberScrollState(),
    onBack: () -> Unit = {},
    onFavorite: () -> Unit = {}
) {
    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                TvShowDetailSkeleton()
                TvShowDetailTopBar(
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
                    TvShowDetailHeader(
                        tvShow = (uiState as? UiState.Success)?.data,
                        isFavorite = isFavorite,
                        scrollState = scrollState,
                        onBackClick = onBack,
                        onFavoriteClick = onFavorite
                    )
                },
                content = { innerPadding ->
                    when (uiState) {
                        is UiState.Success -> {
                            TvShowDetailContent(
                                tvShow = uiState.data,
                                scrollState = scrollState,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        is UiState.Error -> {
                            ErrorLayout(errorMessage = uiState.uiText)
                        }
                        is UiState.Empty -> EmptyState()
                        is UiState.Initial -> Unit
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    TvShowDetailScreen(
        uiState = UiState.Loading,
        isFavorite = false
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    TvShowDetailScreen(
        uiState = UiState.Success(TvShowUiModel(
            name = "The Last of Us",
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
    TvShowDetailScreen(
        uiState = UiState.Error(UiText.DynamicString("Error message")),
        isFavorite = false
    )
}
