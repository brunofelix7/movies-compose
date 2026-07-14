package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import dev.brunofelix.movies.core.presentation.state.MovieCardUiState
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    uiState: MovieUiState,
    onClick: (id: Long) -> Unit = {}
) {
    var cardState by remember { mutableStateOf<MovieCardUiState>(MovieCardUiState.Loading) }
    val shape = RoundedCornerShape(16.dp)

    Card(
        onClick = { onClick(uiState.id) },
        shape = shape,
        border = BorderStroke(1.dp, Colors.white.copy(alpha = 0.2f)),
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uiState.posterPath)
                    .crossfade(true)
                    .build(),
                onState = { state ->
                    cardState = when (state) {
                        is AsyncImagePainter.State.Success -> MovieCardUiState.Success
                        is AsyncImagePainter.State.Loading -> MovieCardUiState.Loading
                        else -> MovieCardUiState.Error
                    }
                },
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.blackPrimary)
            )
            MovieRate(
                rate = uiState.voteAverage,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
            when (cardState) {
                is MovieCardUiState.Loading -> LoadingState()
                is MovieCardUiState.Error -> EmptyImage()
                else -> Unit
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieCardPreview() {
    MovieCard(
        uiState = MovieUiState(
            id = 1L,
            title = "Movie 1",
            voteAverage = 9.1f
        )
    )
}
