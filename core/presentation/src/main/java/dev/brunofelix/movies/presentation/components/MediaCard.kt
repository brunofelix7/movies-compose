package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import dev.brunofelix.movies.designsystem.components.EmptyImage
import dev.brunofelix.movies.designsystem.components.LoadingState
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.shapeRounded12
import dev.brunofelix.movies.designsystem.theme.size150
import dev.brunofelix.movies.domain.model.Media

sealed interface MediaCardState {
    data object Loading : MediaCardState
    data object Success : MediaCardState
    data object Error : MediaCardState
}

/**
 * Poster card of a movie or TV show. The poster carries the title as its content description,
 * since the card has no visible text.
 */
@Composable
fun MediaCard(
    media: Media,
    modifier: Modifier = Modifier,
    onClick: (id: Long) -> Unit = {}
) {
    var cardState by remember {
        mutableStateOf<MediaCardState>(MediaCardState.Loading)
    }

    Card(
        onClick = { onClick(media.id) },
        shape = shapeRounded12,
        modifier = modifier
            .fillMaxWidth()
            .height(size150)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clip(shapeRounded12)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(media.posterPath)
                    .crossfade(true)
                    .build(),
                onState = { state ->
                    cardState = when (state) {
                        is AsyncImagePainter.State.Success -> MediaCardState.Success
                        is AsyncImagePainter.State.Loading -> MediaCardState.Loading
                        else -> MediaCardState.Error
                    }
                },
                contentScale = ContentScale.Crop,
                contentDescription = media.title,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
            when (cardState) {
                is MediaCardState.Loading -> LoadingState()
                // Fills the card so a missing poster reads as a grey placeholder surface
                // instead of a lone glyph floating on the black background.
                is MediaCardState.Error -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    EmptyImage()
                }
                else -> Unit
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaCardPreview() {
    PMovieTheme {
        MediaCard(
            media = Media(
                id = 1L,
                title = "Movie 1",
                voteAverage = 9.1f
            )
        )
    }
}
