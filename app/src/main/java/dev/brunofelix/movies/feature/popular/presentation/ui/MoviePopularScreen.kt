package dev.brunofelix.movies.feature.popular.presentation.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.components.MovieContent
import dev.brunofelix.movies.core.presentation.components.MovieTopBar
import dev.brunofelix.movies.feature.popular.presentation.state.MoviePopularUiState

@Composable
fun MoviePopularScreen(
    uiState: MoviePopularUiState,
    onItemClick: (id: Long) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieTopBar(
                title = stringResource(R.string.populars)
            )
        },
        content = { innerPadding ->
            MovieContent(
                paging = movies,
                paddingValues = innerPadding,
                onClick = { id ->
                    onItemClick(id)
                }
            )
        }
    )
}