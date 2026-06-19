package dev.brunofelix.movies.feature.upcoming.presentation.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.components.MainContent
import dev.brunofelix.movies.core.presentation.components.MainTopBar
import dev.brunofelix.movies.feature.upcoming.presentation.state.MovieUpcomingUiState

@Composable
fun MovieUpcomingScreen(
    uiState: MovieUpcomingUiState,
    onItemClick: (id: Long) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MainTopBar(
                title = stringResource(R.string.upcoming)
            )
        },
        content = { innerPadding ->
            MainContent(
                paging = movies,
                paddingValues = innerPadding,
                onClick = { id ->
                    onItemClick(id)
                }
            )
        }
    )
}