package dev.brunofelix.movies.feature.detail.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.resources.Colors
import dev.brunofelix.movies.feature.detail.presentation.ui.components.DetailHeader
import dev.brunofelix.movies.feature.detail.presentation.ui.components.DetailContent
import dev.brunofelix.movies.feature.detail.presentation.state.DetailUiState

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    uiState: DetailUiState
) {
    Scaffold(
        topBar = {
            DetailHeader(uiState)
        },
        content = { innerPadding ->
            DetailContent(
                uiState = uiState,
                modifier = modifier.padding(innerPadding)
            )
        },
        containerColor = Colors.blackPrimary,
        contentColor = Colors.white
    )
}

@Preview
@Composable
private fun Preview() {
    DetailScreen(
        uiState = DetailUiState(
            state = UiState.Success(MovieUiState()),
            isFavorite = false
        )
    )
}