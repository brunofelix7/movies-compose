package dev.brunofelix.movies.core.presentation.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import dev.brunofelix.movies.core.presentation.components.EmptyImage
import dev.brunofelix.movies.core.presentation.components.LoadingState
import dev.brunofelix.movies.core.presentation.components.MovieRate
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    uiState: MovieUiState,
    onClick: (id: Long) -> Unit = {}
) {
    val cardState = remember {
        mutableStateOf<MovieCardState>(MovieCardState.Loading)
    }

    Box(
        modifier = modifier
    ) {
        if (uiState.isVoteAverageVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .zIndex(2F)
                    .padding(start = 8.dp, bottom = 8.dp)
            ) {
                MovieRate(rate = uiState.voteAverage)
            }
        }
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .padding(4.dp)
                .clickable {
                    onClick(uiState.id)
                }
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(uiState.posterPath)
                        .crossfade(true)
                        .build(),
                    onState = { state ->
                        when (state) {
                            is AsyncImagePainter.State.Loading -> {
                                cardState.value = MovieCardState.Loading
                            }
                            is AsyncImagePainter.State.Success -> {
                                cardState.value = MovieCardState.Success
                            }
                            is AsyncImagePainter.State.Error -> {
                                cardState.value = MovieCardState.Error
                            }
                            is AsyncImagePainter.State.Empty -> {
                                cardState.value = MovieCardState.Error
                            }
                        }
                    },
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .background(Colors.blackPrimary)
                        .clip(RoundedCornerShape(6.dp))
                )
                when (cardState.value) {
                    is MovieCardState.Loading -> LoadingState()
                    is MovieCardState.Error -> EmptyImage()
                    else -> Unit
                }
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
            voteAverage = 9.1F
        ),
        onClick = {}
    )
}