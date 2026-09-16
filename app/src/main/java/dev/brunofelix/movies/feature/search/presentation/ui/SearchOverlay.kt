package dev.brunofelix.movies.feature.search.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.model.enums.MediaType
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.core.presentation.ui.components.CustomSearchBar
import dev.brunofelix.movies.core.presentation.ui.components.EmptyState
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.core.presentation.ui.components.MediaCard
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme
import dev.brunofelix.movies.core.presentation.util.extension.collectAsPreviewLazyPagingItems
import dev.brunofelix.movies.feature.search.presentation.viewmodel.SearchViewModel

data class SearchOverlayUiState(
    val isVisible: Boolean = false,
    val query: String = "",
    val isSearchTriggered: Boolean = false,
    val focusRequester: FocusRequester = FocusRequester()
)

sealed interface SearchOverlayUiAction {
    data class OnQueryChange(val query: String) : SearchOverlayUiAction
    data object OnSearch : SearchOverlayUiAction
    data object OnClose : SearchOverlayUiAction
    data class OnMediaClick(val media: Media) : SearchOverlayUiAction
}

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
    onNavigate: (MainNavKey) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchResults = state.searchResults.collectAsLazyPagingItems()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(isActive) {
        if (isActive) {
            focusRequester.requestFocus()
            keyboardController?.show()
        } else {
            viewModel.onQueryChange("")
        }
    }

    LaunchedEffect(isVisible) {
        if (!isVisible) {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    BackHandler(enabled = isVisible) { onClose() }

    SearchOverlay(
        uiState = SearchOverlayUiState(
            isVisible = isVisible,
            query = state.query,
            isSearchTriggered = state.isSearchTriggered,
            focusRequester = focusRequester
        ),
        searchResults = searchResults,
        paddingValues = paddingValues,
        onAction = { action ->
            when (action) {
                is SearchOverlayUiAction.OnQueryChange -> viewModel.onQueryChange(action.query)
                is SearchOverlayUiAction.OnSearch -> viewModel.onSearch()
                is SearchOverlayUiAction.OnClose -> onClose()
                is SearchOverlayUiAction.OnMediaClick -> {
                    val route = when (action.media.type) {
                        MediaType.MOVIE -> MainNavKey.MovieDetails(action.media.id)
                        MediaType.TV_SHOW -> MainNavKey.TvShowDetails(action.media.id)
                    }
                    onNavigate(route)
                }
            }
        },
        modifier = modifier
    )
}

@Composable
internal fun SearchOverlay(
    uiState: SearchOverlayUiState,
    searchResults: LazyPagingItems<Media>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onAction: (SearchOverlayUiAction) -> Unit = {}
) {
    AnimatedVisibility(
        visible = uiState.isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        GradientBackground(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onAction(SearchOverlayUiAction.OnClose) }
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
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .clickable(enabled = false) {}
                ) {
                    CustomSearchBar(
                        query = uiState.query,
                        containerColor = Color.White.copy(alpha = 0.1F),
                        onQueryChange = { query ->
                            onAction(SearchOverlayUiAction.OnQueryChange(query))
                        },
                        onSearch = { onAction(SearchOverlayUiAction.OnSearch) },
                        modifier = Modifier.focusRequester(uiState.focusRequester)
                    )
                }

                val isSearching = searchResults.loadState.refresh is LoadState.Loading

                when {
                    uiState.query.isBlank() -> Unit
                    // While debouncing, keep whatever is on screen instead of flashing a spinner.
                    !uiState.isSearchTriggered -> Unit
                    isSearching -> LoadingState()
                    searchResults.itemCount == 0 -> EmptyState()
                    else -> SearchResults(
                        searchResults = searchResults,
                        onMediaClick = { media ->
                            onAction(SearchOverlayUiAction.OnMediaClick(media))
                        }
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
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            count = searchResults.itemCount,
            key = searchResults.itemKey { media -> media.id }
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

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    PMovieTheme {
        GradientBackground {
            SearchOverlay(
                uiState = SearchOverlayUiState(
                    isVisible = true,
                    query = "Matrix",
                    isSearchTriggered = true
                ),
                searchResults = listOf(
                    Media(id = 1L, title = "Movie 1"),
                    Media(id = 2L, title = "Movie 2"),
                    Media(id = 3L, title = "Movie 3")
                ).collectAsPreviewLazyPagingItems()
            )
        }
    }
}
