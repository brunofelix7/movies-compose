package dev.brunofelix.movies.feature.popular.presentation.ui

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
import dev.brunofelix.movies.core.presentation.components.GradientBackground
import dev.brunofelix.movies.core.presentation.components.MainContent
import dev.brunofelix.movies.core.presentation.components.MainTopBar
import dev.brunofelix.movies.feature.popular.presentation.state.MoviePopularUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviePopularScreen(
    uiState: MoviePopularUiState,
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
                    title = stringResource(R.string.populars),
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