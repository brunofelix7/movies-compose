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
import dev.brunofelix.movies.presentation.model.TvShowUiModel
import dev.brunofelix.movies.presentation.util.ObserveAsEvents
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.presentation.detail.components.TvShowDetailContent

private const val BACKDROP_SCREEN_FRACTION = 0.75f
private const val COLLAPSE_RANGE_FRACTION = 0.5f
private const val SKELETON_INFO_CHIPS = 4

@Composable
internal fun TvShowDetailRoute(
    tvShowId: Long,
    onBack: () -> Unit,
    viewModel: TvShowDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(tvShowId) {
        viewModel.onAction(TvShowDetailUiAction.OnLoad(tvShowId))
    }

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            TvShowDetailUiEvent.NavigateBack -> onBack()
            is TvShowDetailUiEvent.ShowToast -> {
                Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    TvShowDetailScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun TvShowDetailScreen(
    uiState: TvShowDetailUiState,
    onAction: (TvShowDetailUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val onBack = { onAction(TvShowDetailUiAction.OnBack) }

    when (val tvShowState = uiState.tvShow) {
        is UiState.Initial -> Unit
        is UiState.Loading -> DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
            DetailSkeleton(infoChipCount = SKELETON_INFO_CHIPS)
        }
        is UiState.Error -> DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
            ErrorLayout(errorMessage = tvShowState.uiText, onRetry = { onAction(TvShowDetailUiAction.OnRetry) })
        }
        is UiState.Empty -> DetailStatusLayout(modifier = modifier, onBackClick = onBack) {
            EmptyState()
        }
        is UiState.Success -> {
            val tvShow = tvShowState.data
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
                    TvShowDetailContent(
                        tvShow = tvShow,
                        seasonsState = uiState.seasons,
                        onSeasonToggle = { onAction(TvShowDetailUiAction.OnSeasonToggle(it)) },
                        onSeasonRetry = { onAction(TvShowDetailUiAction.OnSeasonRetry(it)) },
                        scrollState = scrollState,
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                    )
                    DetailTopBar(
                        title = tvShow.name,
                        scrollFraction = collapseFraction,
                        isFavorite = uiState.isFavorite,
                        onBackClick = onBack,
                        onFavoriteClick = { onAction(TvShowDetailUiAction.OnFavoriteToggle) },
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
        TvShowDetailScreen(uiState = TvShowDetailUiState(tvShow = UiState.Loading), onAction = {})
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    PMovieTheme {
        TvShowDetailScreen(
            uiState = TvShowDetailUiState(
                tvShow = UiState.Success(
                    TvShowUiModel(
                        name = "The Last of Us",
                        genres = listOf(MovieGenre(name = "Action"), MovieGenre(name = "Drama"))
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
        TvShowDetailScreen(uiState = TvShowDetailUiState(tvShow = UiState.Empty), onAction = {})
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    PMovieTheme {
        TvShowDetailScreen(
            uiState = TvShowDetailUiState(tvShow = UiState.Error(UiText.DynamicString("Error message"))),
            onAction = {}
        )
    }
}
