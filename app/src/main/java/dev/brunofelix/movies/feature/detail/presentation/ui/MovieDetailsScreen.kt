package dev.brunofelix.movies.feature.detail.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.core.presentation.components.card.MovieCard
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.resources.Colors
import dev.brunofelix.movies.feature.detail.presentation.components.MovieDetailsContent
import dev.brunofelix.movies.feature.detail.presentation.components.MovieDetailsTopBar
import dev.brunofelix.movies.feature.detail.presentation.components.MovieDetailsTopBarImage
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: StateFlow<UiState<MovieUiState>>,
    isFavorite: StateFlow<Boolean>,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onHideVoteAverage: () -> Unit
) {
    val uiState by uiState.collectAsStateWithLifecycle()
    val isFavorite by isFavorite.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Box {
                MovieDetailsTopBarImage(uiState = uiState)
                MovieDetailsTopBar(
                    isFavorite = isFavorite,
                    onBackClick = onBackClick,
                    onFavoriteClick = onFavoriteClick
                )
                (uiState as? UiState.Success)?.let {
                    MovieCard(
                        uiState = it.data,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth(0.50F)
                            .align(Alignment.BottomStart)
                            .offset(y = 100.dp)
                    )
                }
            }
        },
        content = { innerPadding ->
            MovieDetailsContent(
                modifier = modifier.padding(innerPadding),
                uiState = uiState,
                onHideVoteAverage = onHideVoteAverage
            )
        },
        containerColor = Colors.blackPrimary,
        contentColor = Colors.white
    )
}

@Preview
@Composable
private fun MovieDetailsScreenPreview() {

}