package dev.brunofelix.movies.feature.detail.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.MovieCard
import dev.brunofelix.movies.feature.detail.presentation.state.DetailUiActions
import dev.brunofelix.movies.feature.detail.presentation.state.DetailUiState

@Composable
fun DetailHeader(
    state: DetailUiState,
    actions: DetailUiActions,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        if (state.uiState is UiState.Success) {
            DetailTopBarImage(uiState = state)
        }
        DetailTopBar(
            isFavorite = state.isFavorite,
            shouldShowFavorite = state.uiState is UiState.Success,
            onBackClick = actions.onBack,
            onFavoriteClick = actions.onFavorite
        )
        (state.uiState as? UiState.Success)?.let {
            MovieCard(
                uiState = it.data,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(0.45F)
                    .height(220.dp)
                    .align(Alignment.BottomStart)
                    .offset(y = 80.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Scaffold(
        topBar = {
            DetailHeader(
                state = DetailUiState(
                    uiState = UiState.Success(MovieUiState()),
                    isFavorite = false
                ),
                actions = DetailUiActions()
            )
        },
        content = { innerPadding ->
            GradientBackground {
                Box(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    )
}