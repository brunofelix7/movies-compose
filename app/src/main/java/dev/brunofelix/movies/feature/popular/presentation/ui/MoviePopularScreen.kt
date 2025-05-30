package dev.brunofelix.movies.feature.popular.presentation.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.components.card.MovieCardContent
import dev.brunofelix.movies.core.presentation.ui.components.MovieTopBar
import dev.brunofelix.movies.feature.popular.presentation.viewmodel.state.MoviePopularUiState

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
            MovieCardContent(
                paging = movies,
                paddingValues = innerPadding,
                onClick = { id ->
                    onItemClick(id)
                }
            )
        }
    )
}