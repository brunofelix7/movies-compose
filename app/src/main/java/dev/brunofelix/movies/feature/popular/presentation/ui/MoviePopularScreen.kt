package dev.brunofelix.movies.feature.popular.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import dev.brunofelix.movies.core.presentation.ui.components.MainContent
import dev.brunofelix.movies.feature.popular.presentation.state.MoviePopularUiState

@Composable
fun MoviePopularScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    uiState: MoviePopularUiState,
    onItemClick: (id: Long) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()

    MainContent(
        modifier = modifier,
        paging = movies,
        paddingValues = paddingValues,
        onClick = { id ->
            onItemClick(id)
        }
    )
}
