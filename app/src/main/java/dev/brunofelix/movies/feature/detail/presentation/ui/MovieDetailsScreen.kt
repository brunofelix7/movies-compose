package dev.brunofelix.movies.feature.detail.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.resources.Colors
import dev.brunofelix.movies.feature.detail.presentation.components.MovieDetailsContent
import dev.brunofelix.movies.feature.detail.presentation.components.MovieDetailsTopBar
import dev.brunofelix.movies.feature.detail.presentation.components.MovieDetailsTopBarImage
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: StateFlow<UiState<Movie>>,
    isFavorite: StateFlow<Boolean>,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
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
            }
        },
        content = { innerPadding ->
            MovieDetailsContent(
                modifier = modifier.padding(innerPadding),
                uiState = uiState
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