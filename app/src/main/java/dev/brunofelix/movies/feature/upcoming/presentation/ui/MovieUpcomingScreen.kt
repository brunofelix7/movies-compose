package dev.brunofelix.movies.feature.upcoming.presentation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.MainContent
import dev.brunofelix.movies.core.presentation.ui.components.MainTopBar
import dev.brunofelix.movies.feature.upcoming.presentation.state.MovieUpcomingUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieUpcomingScreen(
    uiState: MovieUpcomingUiState,
    onItemClick: (id: Long) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    GradientBackground {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            topBar = {
                MainTopBar(
                    title = stringResource(R.string.upcoming),
                    scrollBehavior = scrollBehavior
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
}