package dev.brunofelix.movies.core.presentation.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.components.card.MovieCard
import dev.brunofelix.movies.core.presentation.mapper.toUiState

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
                .padding(top = 8.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = paddingValues,
                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (paging != null) {
                    items(paging.itemCount) { index ->
                        val movie = paging[index]
                        movie?.let {
                            MovieCard(
                                uiState = movie.toUiState(),
                                onClick = { id ->
                                    onClick(id)
                                }
                            )
                        }
                    }
                    paging.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    LoadingState()
                                }
                            }
                            loadState.append is LoadState.Loading -> {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    LoadingState()
                                }
                            }
                            loadState.refresh is LoadState.Error -> {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    ErrorView(
                                        message = stringResource(R.string.error_message),
                                        onRetry = { retry() }
                                    )
                                }
                            }
                            loadState.append is LoadState.Error -> {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    ErrorView(
                                        message = stringResource(R.string.error_title),
                                        onRetry = { retry() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MainContent(
        paging = null,
        paddingValues = PaddingValues(),
        onClick = {}
    )
}