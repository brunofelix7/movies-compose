package dev.brunofelix.pmovie.feature.movie.presentation.components

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
import dev.brunofelix.pmovie.core.presentation.components.EmptyImage
import dev.brunofelix.pmovie.core.presentation.components.LoadingView
import dev.brunofelix.pmovie.core.presentation.ui.Colors
import dev.brunofelix.pmovie.feature.movie.domain.model.Movie
import dev.brunofelix.pmovie.feature.movie.presentation.state.MovieItemUiState

@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    onItemClick: (id: Long) -> Unit
) {
    val uiState = remember {
        mutableStateOf<MovieItemUiState>(MovieItemUiState.Loading)
    }

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .zIndex(2F)
                .padding(start = 8.dp, bottom = 8.dp)
        ) {
            MovieRate(rate = movie.voteAverage)
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
                        .data(movie.imageUrl)
                        .crossfade(true)
                        .build(),
                    onState = { state ->
                        when (state) {
                            is AsyncImagePainter.State.Loading -> {
                                uiState.value = MovieItemUiState.Loading
                            }
                            is AsyncImagePainter.State.Success -> {
                                uiState.value = MovieItemUiState.Success
                            }
                            is AsyncImagePainter.State.Error -> {
                                uiState.value = MovieItemUiState.Error
                            }
                            is AsyncImagePainter.State.Empty -> {
                                uiState.value = MovieItemUiState.Error
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
                    is MovieItemUiState.Loading -> LoadingView()
                    is MovieItemUiState.Error -> EmptyImage()
                    else -> Unit
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    val movie = Movie(id = 1L, title = "Movie 1", imageUrl = "", voteAverage = 9.1F)
    MovieItem(movie) {

    }
}