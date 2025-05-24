package dev.brunofelix.pmovie.feature.movie.presentation.view

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import dev.brunofelix.pmovie.R
import dev.brunofelix.pmovie.feature.movie.presentation.components.MovieContent
import dev.brunofelix.pmovie.feature.movie.presentation.components.MovieTopBar
import dev.brunofelix.pmovie.feature.movie.presentation.state.MovieUpcomingUiState

@Composable
fun MovieUpcomingScreen(
    uiState: MovieUpcomingUiState,
    onItemClick: (id: Long) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieTopBar(
                title = stringResource(R.string.upcoming)
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