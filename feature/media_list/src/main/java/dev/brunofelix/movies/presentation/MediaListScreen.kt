package dev.brunofelix.movies.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.brunofelix.movies.designsystem.components.GradientBackground
import dev.brunofelix.movies.designsystem.components.SecondaryTopBar
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.presentation.components.MainContent
import dev.brunofelix.movies.presentation.mapper.titleResId
import dev.brunofelix.movies.presentation.util.ObserveAsEvents
import dev.brunofelix.movies.presentation.util.extension.collectAsPreviewLazyPagingItems

@Composable
internal fun MediaListRoute(
    category: MediaListCategory,
    month: ReleaseMonth?,
    onNavigateToDetails: (Media) -> Unit,
    onBack: () -> Unit,
    viewModel: MediaListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val medias = viewModel.medias.collectAsLazyPagingItems()

    LaunchedEffect(category, month) {
        viewModel.onAction(MediaListUiAction.OnLoad(category, month))
    }

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            MediaListUiEvent.NavigateBack -> onBack()
            is MediaListUiEvent.NavigateToDetails -> onNavigateToDetails(event.media)
        }
    }

    MediaListScreen(
        uiState = uiState,
        medias = medias,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun MediaListScreen(
    uiState: MediaListUiState,
    medias: LazyPagingItems<Media>,
    onAction: (MediaListUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            SecondaryTopBar(
                title = uiState.category?.let { stringResource(it.titleResId) }.orEmpty(),
                onBack = { onAction(MediaListUiAction.OnBack) }
            )
        }
    ) { innerPadding ->
        MainContent(
            paging = medias,
            paddingValues = PaddingValues(bottom = spacing16),
            onClick = { media -> onAction(MediaListUiAction.OnMediaClick(media)) },
            media = { it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

private val previewState = MediaListUiState(category = MediaListCategory.MOVIE_TOP_RATED)

@Composable
private fun PreviewContainer(medias: LazyPagingItems<Media>) {
    PMovieTheme {
        GradientBackground {
            MediaListScreen(uiState = previewState, medias = medias, onAction = {})
        }
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    PreviewContainer(
        listOf(
            Media(id = 1L, title = "Movie 1"),
            Media(id = 2L, title = "Movie 2")
        ).collectAsPreviewLazyPagingItems()
    )
}

@Preview
@Composable
private fun LoadingPreview() {
    PreviewContainer(emptyList<Media>().collectAsPreviewLazyPagingItems(refresh = LoadState.Loading))
}

@Preview
@Composable
private fun EmptyPreview() {
    PreviewContainer(emptyList<Media>().collectAsPreviewLazyPagingItems())
}

@Preview
@Composable
private fun ErrorPreview() {
    PreviewContainer(
        emptyList<Media>().collectAsPreviewLazyPagingItems(refresh = LoadState.Error(IllegalStateException()))
    )
}
