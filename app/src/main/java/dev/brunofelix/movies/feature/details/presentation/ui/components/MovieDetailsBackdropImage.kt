package dev.brunofelix.movies.feature.details.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.brunofelix.movies.core.presentation.ui.components.LoadingView
import dev.brunofelix.movies.core.presentation.ui.components.empty.EmptyImage
import dev.brunofelix.movies.core.presentation.ui.resources.Colors
import dev.brunofelix.movies.feature.details.presentation.viewmodel.state.MovieDetailsUiState

@Composable
fun MovieDetailsBackdropImage(
    modifier: Modifier = Modifier,
    uiState: MovieDetailsUiState,
) {
    val backdropPath = remember { mutableStateOf<String?>("") }

    Box(
        modifier = modifier.background(Colors.blackPrimary),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(backdropPath.value)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.Center)
        )
        uiState.let {
            when (it) {
                is MovieDetailsUiState.Loading -> LoadingView()
                is MovieDetailsUiState.Success -> {
                    backdropPath.value = it.movie?.details?.backdropPath
                    if (backdropPath.value?.isEmpty() == true) {
                        EmptyImage()
                    }
                }
                is MovieDetailsUiState.Error -> EmptyImage()
                else -> Unit
            }
        }
    }
}