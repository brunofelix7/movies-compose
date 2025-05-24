package dev.brunofelix.pmovie.feature.movie.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.pmovie.core.presentation.ui.Colors
import dev.brunofelix.pmovie.feature.movie.presentation.components.details.MovieDetailsBackdropImage
import dev.brunofelix.pmovie.feature.movie.presentation.components.details.MovieDetailsContent
import dev.brunofelix.pmovie.feature.movie.presentation.components.details.MovieDetailsTopBar
import dev.brunofelix.pmovie.feature.movie.presentation.state.MovieDetailsUiState

@Composable
fun MovieDetailsScreen(
    movieId: Long,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    uiState: MovieDetailsUiState?
) {
    Scaffold(
        topBar = {
            Box {
                MovieDetailsBackdropImage(uiState = uiState)
                MovieDetailsTopBar(
                    onBackClick = onBackClick
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