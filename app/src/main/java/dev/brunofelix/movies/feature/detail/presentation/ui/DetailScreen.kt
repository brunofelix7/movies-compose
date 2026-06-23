package dev.brunofelix.movies.feature.detail.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.presentation.components.GradientBackground
import dev.brunofelix.movies.core.presentation.components.LoadingState
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.feature.detail.presentation.state.DetailUiState
import dev.brunofelix.movies.feature.detail.presentation.ui.components.DetailContent
import dev.brunofelix.movies.feature.detail.presentation.ui.components.DetailHeader

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    uiState: DetailUiState
) {
    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                DetailHeader(uiState)
            },
            content = { innerPadding ->
                when (uiState.state) {
                    is UiState.Loading -> LoadingState()
                    is UiState.Success -> {
                        DetailContent(
                            uiState = uiState,
                            modifier = modifier.padding(innerPadding)
                        )
                    }
                    is UiState.Error -> {
                        // TODO: Add error layout
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    DetailScreen(
        uiState = DetailUiState(
            state = UiState.Loading,
            isFavorite = false
        )
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    DetailScreen(
        uiState = DetailUiState(
            state = UiState.Success(MovieUiState()),
            isFavorite = false
        )
    )
}