package dev.brunofelix.movies.feature.movie.presentation.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.brunofelix.movies.core.presentation.components.EmptyImage
import dev.brunofelix.movies.core.presentation.components.LoadingView
import dev.brunofelix.movies.core.presentation.resources.Colors
import dev.brunofelix.movies.feature.movie.presentation.state.MovieDetailsUiState

@Composable
fun MovieDetailsCoverImage(
    modifier: Modifier = Modifier,
    uiState: MovieDetailsUiState
) {
    val imageUrl = remember { mutableStateOf<String?>("") }

    Box(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl.value)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .background(Colors.blackPrimary)
                        .clip(RoundedCornerShape(6.dp))
                )
                when (uiState) {
                    is MovieDetailsUiState.Loading -> LoadingView()
                    is MovieDetailsUiState.Success -> {
                        imageUrl.value = uiState.movie?.imageUrl
                        if (imageUrl.value?.isEmpty() == true) {
                            EmptyImage()
                        }
                    }
                    is MovieDetailsUiState.Error -> EmptyImage()
                    else -> Unit
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsCoverImagePreview() {
    MovieDetailsCoverImage(uiState = MovieDetailsUiState.Loading)
}