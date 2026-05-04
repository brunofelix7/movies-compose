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
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.components.EmptyImage
import dev.brunofelix.movies.core.presentation.components.LoadingState
import dev.brunofelix.movies.core.presentation.components.MovieRate
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    onItemClick: (id: Long) -> Unit = {}
) {
    val uiState = remember {
        mutableStateOf<MovieCardState>(MovieCardState.Loading)
    }

    Box(
        modifier = modifier
    ) {
        if (movie.isVoteAverageVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .zIndex(2F)
                    .padding(start = 8.dp, bottom = 8.dp)
            ) {
                MovieRate(rate = movie.voteAverage)
            }
        }
        Card(
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(4.dp)
                .clickable {
                    onItemClick(movie.id)
                }
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.posterPath)
                        .crossfade(true)
                        .build(),
                    onState = { state ->
                        when (state) {
                            is AsyncImagePainter.State.Loading -> {
                                uiState.value = MovieCardState.Loading
                            }
                            is AsyncImagePainter.State.Success -> {
                                uiState.value = MovieCardState.Success
                            }
                            is AsyncImagePainter.State.Error -> {
                                uiState.value = MovieCardState.Error
                            }
                            is AsyncImagePainter.State.Empty -> {
                                uiState.value = MovieCardState.Error
                            }
                        }
                    },
                    contentScale = ContentScale.FillHeight,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .background(Colors.blackPrimary)
                        .clip(RoundedCornerShape(6.dp))
                )
                when (uiState.value) {
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
        movie = Movie(id = 1L, title = "Movie 1", posterPath = "", voteAverage = 9.1F),
        onItemClick = {}
    )
}