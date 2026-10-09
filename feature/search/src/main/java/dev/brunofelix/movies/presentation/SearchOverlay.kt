package dev.brunofelix.movies.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.brunofelix.movies.designsystem.components.CustomSearchBar
import dev.brunofelix.movies.designsystem.components.EmptyState
import dev.brunofelix.movies.designsystem.components.GradientBackground
import dev.brunofelix.movies.designsystem.components.LoadingState
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.SurfaceGlassStrong
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing32
import dev.brunofelix.movies.designsystem.theme.spacing8
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.presentation.components.MediaCard
import dev.brunofelix.movies.presentation.util.ObserveAsEvents
import dev.brunofelix.movies.presentation.util.extension.collectAsPreviewLazyPagingItems

const val SEARCH_OVERLAY_TEST_TAG = "search_overlay"

private const val GRID_COLUMNS = 3

/**
 * Hosts the [SearchOverlay] and keeps the keyboard in sync with it.
 *
 * @param isActive whether the search session is running. The query and the results are only
 * dropped when it ends, so navigating to a detail screen and coming back keeps them.
 * @param isVisible whether the overlay is currently on screen. It is hidden while the user is
 * away on another destination, without ending the session.
 */
@Composable
fun SearchOverlayRoute(
    isActive: Boolean,
    isVisible: Boolean,
    onClose: () -> Unit,
    onNavigateToDetails: (Media) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            SearchUiEvent.Close -> onClose()
            is SearchUiEvent.NavigateToDetails -> onNavigateToDetails(event.media)
        }
    }

    LaunchedEffect(isActive) {
        if (!isActive) viewModel.onAction(SearchUiAction.OnSessionEnd)
    }

    // Follows visibility rather than the session, so coming back from a detail screen puts
    // the caret and the keyboard back on the search bar.
    LaunchedEffect(isVisible) {
        if (isVisible) {
            focusRequester.requestFocus()
            keyboardController?.show()
        } else {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    BackHandler(enabled = isVisible) { viewModel.onAction(SearchUiAction.OnClose) }

    SearchOverlay(
        uiState = uiState,
        searchResults = searchResults,
        isVisible = isVisible,
        onAction = viewModel::onAction,
        modifier = modifier,
        paddingValues = paddingValues,
        focusRequester = focusRequester
    )
}

@Composable
internal fun SearchOverlay(
    uiState: SearchUiState,
    searchResults: LazyPagingItems<Media>,
    isVisible: Boolean,
    onAction: (SearchUiAction) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        GradientBackground(
            modifier = Modifier
                .testTag(SEARCH_OVERLAY_TEST_TAG)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onAction(SearchUiAction.OnClose) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
            ) {
                Box(
                    modifier = Modifier
                        .padding(spacing16)
                        .clickable(enabled = false) {}
                ) {
                    CustomSearchBar(
                        query = uiState.query,
                        containerColor = SurfaceGlassStrong,
                        onQueryChange = { query -> onAction(SearchUiAction.OnQueryChange(query)) },
                        onSearch = { onAction(SearchUiAction.OnSearch) },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                }

                val isSearching = searchResults.loadState.refresh is LoadState.Loading

                when {
                    uiState.query.isBlank() -> Unit
                    // While debouncing, keep whatever is on screen instead of flashing a spinner.
                    !uiState.isSearchTriggered -> Unit
                    isSearching -> LoadingState(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.padding(top = spacing32)
                    )
                    searchResults.itemCount == 0 -> EmptyState(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.padding(top = spacing32)
                    )
                    else -> SearchResults(
                        searchResults = searchResults,
                        onMediaClick = { media -> onAction(SearchUiAction.OnMediaClick(media)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchResults(
    searchResults: LazyPagingItems<Media>,
    onMediaClick: (Media) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(GRID_COLUMNS),
        contentPadding = PaddingValues(start = spacing16, end = spacing16, bottom = spacing16),
        horizontalArrangement = Arrangement.spacedBy(spacing8, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(spacing8),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            count = searchResults.itemCount,
            key = searchResults.itemKey { media -> "${media.type}-${media.id}" }
        ) { index ->
            searchResults[index]?.let { media ->
                MediaCard(
                    media = media,
                    onClick = { onMediaClick(media) }
                )
            }
        }
    }
}

private val previewResults = listOf(
    Media(id = 1L, title = "Movie 1"),
    Media(id = 2L, title = "Movie 2"),
    Media(id = 3L, title = "Movie 3")
)

@Preview
@Composable
private fun IdlePreview() {
    PMovieTheme {
        SearchOverlay(
            uiState = SearchUiState(),
            searchResults = emptyList<Media>().collectAsPreviewLazyPagingItems(),
            isVisible = true,
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    PMovieTheme {
        SearchOverlay(
            uiState = SearchUiState(query = "Matrix", isSearchTriggered = true),
            searchResults = emptyList<Media>().collectAsPreviewLazyPagingItems(refresh = LoadState.Loading),
            isVisible = true,
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    PMovieTheme {
        SearchOverlay(
            uiState = SearchUiState(query = "Matrix", isSearchTriggered = true),
            searchResults = previewResults.collectAsPreviewLazyPagingItems(),
            isVisible = true,
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun EmptyPreview() {
    PMovieTheme {
        SearchOverlay(
            uiState = SearchUiState(query = "Matrix", isSearchTriggered = true),
            searchResults = emptyList<Media>().collectAsPreviewLazyPagingItems(),
            isVisible = true,
            onAction = {}
        )
    }
}
