package dev.brunofelix.movies.core.presentation.ui.components

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
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.mapper.toUiState
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme
import dev.brunofelix.movies.core.presentation.util.extension.collectAsPreviewLazyPagingItems

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    paging: LazyPagingItems<Movie>?,
    paddingValues: PaddingValues,
    onClick: (id: Long) -> Unit
) {
    GradientBackground {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            if (paging == null) return@Box
            val loadState = paging.loadState

            // 1. Initial Load States (Refresh)
            when (loadState.refresh) {
                is LoadState.Loading -> BoxCenter { LoadingState() }
                is LoadState.Error -> BoxCenter { PagingRetry(onRetry = { paging.retry() }) }
                is LoadState.NotLoading -> {
                    if (paging.itemCount == 0) {
                        EmptyState()
                    } else {
                        // 2. Data is loaded, show the grid
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            contentPadding = paddingValues,
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterHorizontally
                            ),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(paging.itemCount) { index ->
                                val movie = paging[index]
                                movie?.let {
                                    MovieCard(
                                        uiState = movie.toUiState(),
                                        onClick = { id -> onClick(id) }
                                    )
                                }
                            }

                            // 3. Pagination States (Append)
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                when (loadState.append) {
                                    is LoadState.Loading -> {
                                        LoadingState(
                                            modifier = Modifier.padding(vertical = 16.dp)
                                        )
                                    }
                                    is LoadState.Error -> {
                                        PagingRetry(
                                            modifier = Modifier.padding(vertical = 16.dp),
                                            onRetry = { paging.retry() }
                                        )
                                    }
                                    is LoadState.NotLoading -> Unit
                                }
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

@Preview(showBackground = true, name = "Success")
@Composable
private fun SuccessPreview() {
    PMovieTheme {
        MainContent(
            paging = Movie.mocks().collectAsPreviewLazyPagingItems(),
            paddingValues = PaddingValues(),
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Empty")
@Composable
private fun EmptyPreview() {
    PMovieTheme {
        MainContent(
            paging = emptyList<Movie>().collectAsPreviewLazyPagingItems(),
            paddingValues = PaddingValues(),
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Initial Loading")
@Composable
private fun InitialLoadingPreview() {
    PMovieTheme {
        MainContent(
            paging = emptyList<Movie>().collectAsPreviewLazyPagingItems(
                refresh = LoadState.Loading
            ),
            paddingValues = PaddingValues(),
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Initial Error")
@Composable
private fun InitialErrorPreview() {
    PMovieTheme {
        MainContent(
            paging = emptyList<Movie>().collectAsPreviewLazyPagingItems(
                refresh = LoadState.Error(Exception())
            ),
            paddingValues = PaddingValues(),
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Pagination Loading")
@Composable
private fun LoadingPreview() {
    PMovieTheme {
        MainContent(
            paging = Movie.mocks().collectAsPreviewLazyPagingItems(
                append = LoadState.Loading
            ),
            paddingValues = PaddingValues(bottom = 80.dp),
            onClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Pagination Error")
@Composable
private fun ErrorPreview() {
    PMovieTheme {
        MainContent(
            paging = Movie.mocks().collectAsPreviewLazyPagingItems(
                append = LoadState.Error(Exception())
            ),
            paddingValues = PaddingValues(bottom = 80.dp),
            onClick = {}
        )
    }
}
