package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import dev.brunofelix.movies.designsystem.components.EmptyState
import dev.brunofelix.movies.designsystem.components.LoadingState
import dev.brunofelix.movies.designsystem.components.PagingRetry
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing8
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.util.extension.toMedia
import dev.brunofelix.movies.presentation.util.extension.collectAsPreviewLazyPagingItems
import dev.brunofelix.movies.presentation.util.extension.createCombinedLoadStates

private const val GRID_COLUMNS = 3

/**
 * Paginated poster grid, with the loading, error and empty states of the first page and the
 * footer of the next one.
 */
@Composable
fun <T : Any> MainContent(
    paging: LazyPagingItems<T>?,
    paddingValues: PaddingValues,
    onClick: (T) -> Unit,
    media: (T) -> Media,
    modifier: Modifier = Modifier,
    loadState: CombinedLoadStates? = paging?.loadState,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing16, vertical = spacing8)
    ) {
        if (paging == null || loadState == null) return@Box

        when (loadState.refresh) {
            is LoadState.Loading -> BoxCenter { LoadingState() }
            is LoadState.Error -> BoxCenter { PagingRetry(onRetry = { paging.retry() }) }
            is LoadState.NotLoading -> {
                if (paging.itemCount == 0) {
                    EmptyState()
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(GRID_COLUMNS),
                        contentPadding = paddingValues,
                        horizontalArrangement = Arrangement.spacedBy(
                            spacing8,
                            Alignment.CenterHorizontally
                        ),
                        verticalArrangement = Arrangement.spacedBy(spacing8),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            count = paging.itemCount,
                            contentType = paging.itemContentType { "card" }
                        ) { index ->
                            paging[index]?.let { item ->
                                MediaCard(
                                    media = media(item),
                                    onClick = { onClick(item) }
                                )
                            }
                        }

                        item(span = { GridItemSpan(maxLineSpan) }) {
                            when (loadState.append) {
                                is LoadState.Loading -> LoadingState(
                                    modifier = Modifier.padding(vertical = spacing16)
                                )
                                is LoadState.Error -> PagingRetry(
                                    modifier = Modifier.padding(vertical = spacing16),
                                    onRetry = { paging.retry() }
                                )
                                is LoadState.NotLoading -> Unit
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BoxCenter(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun SuccessPreview() {
    PMovieTheme {
        MainContent(
            paging = Movie.mocks().collectAsPreviewLazyPagingItems(),
            loadState = createCombinedLoadStates(),
            paddingValues = PaddingValues(),
            onClick = {},
            media = { it.toMedia() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    PMovieTheme {
        MainContent(
            paging = emptyList<Movie>().collectAsPreviewLazyPagingItems(),
            loadState = createCombinedLoadStates(refresh = LoadState.Loading),
            paddingValues = PaddingValues(),
            onClick = {},
            media = { it.toMedia() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyPreview() {
    PMovieTheme {
        MainContent(
            paging = emptyList<Movie>().collectAsPreviewLazyPagingItems(),
            loadState = createCombinedLoadStates(),
            paddingValues = PaddingValues(),
            onClick = {},
            media = { it.toMedia() }
        )
    }
}
