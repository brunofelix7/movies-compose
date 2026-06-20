package dev.brunofelix.movies.feature.favorite.presentation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.components.GradientBackground
import dev.brunofelix.movies.core.presentation.components.MainTopBar
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.feature.favorite.presentation.components.FavoriteContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    uiState: StateFlow<UiState<List<Movie>>>,
    onClick: (Long) -> Unit
) {
    val uiState by uiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    GradientBackground {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            topBar = {
                MainTopBar(
                    title = stringResource(R.string.favorites),
                    scrollBehavior = scrollBehavior
                )
            },
            content = { innerPadding ->
                FavoriteContent(
                    modifier = modifier,
                    paddingValues = innerPadding,
                    uiState = uiState,
                    onClick = onClick
                )
            }
        )
    }
}

@Preview
@Composable
private fun FavoriteScreenPreview() {
    FavoriteScreen(
        uiState = MutableStateFlow(UiState.Empty),
        onClick = { }
    )
}