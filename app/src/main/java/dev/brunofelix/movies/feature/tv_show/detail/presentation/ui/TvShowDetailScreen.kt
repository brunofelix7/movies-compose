package dev.brunofelix.movies.feature.tv_show.detail.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

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
import dev.brunofelix.movies.core.presentation.ui.model.TvShowUiModel
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.core.presentation.util.UiText
import dev.brunofelix.movies.feature.tv_show.detail.presentation.state.SeasonsState
import dev.brunofelix.movies.feature.tv_show.detail.presentation.ui.components.TvShowDetailContent

@Composable
fun TvShowDetailRoute(
    tvShowId: Long,
    onBack: () -> Unit,
    viewModel: TvShowDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    val seasonsState by viewModel.seasonsState.collectAsStateWithLifecycle()

    LaunchedEffect(tvShowId) {
        viewModel.getDetails(tvShowId)
    }

    TvShowDetailScreen(
        uiState = uiState,
        isFavorite = isFavorite,
        seasonsState = seasonsState,
        onBack = onBack,
        onFavorite = { viewModel.onFavoriteToggle() },
        onSeasonToggle = viewModel::onSeasonToggle,
        onSeasonRetry = viewModel::onSeasonRetry,
        onRetry = { viewModel.getDetails(tvShowId) }
    )
}

@Composable
private fun TvShowDetailScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<TvShowUiModel>,
    isFavorite: Boolean,
    seasonsState: SeasonsState = SeasonsState(),
    onBack: () -> Unit = {},
    onFavorite: () -> Unit = {},
    onSeasonToggle: (Int) -> Unit = {},
    onSeasonRetry: (Int) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    when (uiState) {
        is UiState.Initial -> Unit
        is UiState.Loading -> {
            DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
                DetailSkeleton(infoChipCount = 4)
            }
        }
        is UiState.Error -> {
            DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
                ErrorLayout(errorMessage = uiState.uiText, onRetry = onRetry)
            }
        }
        is UiState.Empty -> {
            DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
                EmptyState()
            }
        }
        is UiState.Success -> {
            val tvShow = uiState.data
            val scrollState = androidx.compose.foundation.rememberScrollState()
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp
            val imageHeight = screenHeight * 0.75f
            val collapseRange = with(LocalDensity.current) { (imageHeight * 0.5f).toPx() }
            val collapseFraction = if (collapseRange > 0f) (scrollState.value / collapseRange).coerceIn(0f, 1f) else 0f

            Scaffold(
                modifier = modifier,
                containerColor = Colors.blackPrimary,
                content = { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        TvShowDetailContent(
                            tvShow = tvShow,
                            seasonsState = seasonsState,
                            onSeasonToggle = onSeasonToggle,
                            onSeasonRetry = onSeasonRetry,
                            scrollState = scrollState,
                            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                        )
                        
                        dev.brunofelix.movies.core.presentation.ui.components.DetailTopBar(
                            title = tvShow.name,
                            scrollFraction = collapseFraction,
                            isFavorite = isFavorite,
                            shouldShowFavorite = true,
                            onBackClick = onBack,
                            onFavoriteClick = onFavorite,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                
                        )
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


