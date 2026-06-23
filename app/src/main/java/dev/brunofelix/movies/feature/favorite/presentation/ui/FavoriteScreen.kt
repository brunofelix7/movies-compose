package dev.brunofelix.movies.feature.favorite.presentation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.feature.favorite.presentation.state.FavoriteUiState
import dev.brunofelix.movies.feature.favorite.presentation.ui.components.FavoriteContent
import dev.brunofelix.movies.feature.favorite.presentation.ui.components.FavoriteHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    uiState: FavoriteUiState
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    GradientBackground {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            topBar = {
                FavoriteHeader(scrollBehavior)
            },
            content = { innerPadding ->
                FavoriteContent(
                    modifier = modifier,
                    paddingValues = innerPadding,
                    uiState = uiState
                )
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    FavoriteScreen(
        uiState = FavoriteUiState()
    )
}