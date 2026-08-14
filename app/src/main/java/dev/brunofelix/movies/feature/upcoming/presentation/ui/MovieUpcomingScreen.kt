package dev.brunofelix.movies.feature.upcoming.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import dev.brunofelix.movies.core.presentation.ui.components.MainContent
import dev.brunofelix.movies.feature.upcoming.presentation.state.MovieUpcomingUiState

@Composable
fun MovieUpcomingScreen(
    uiState: MovieUpcomingUiState,
    onItemClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
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
