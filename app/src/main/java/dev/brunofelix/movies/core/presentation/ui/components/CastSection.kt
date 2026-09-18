package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.model.CastUiModel
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme

private val PhotoSize = 88.dp

/**
 * Horizontal rail with the actors credited in a movie or TV show, top billed first.
 */
@Composable
fun CastSection(
    cast: List<CastUiModel>,
    modifier: Modifier = Modifier
) {
    if (cast.isEmpty()) return

    SectionCard(
        title = stringResource(R.string.cast_title),
        modifier = modifier
    ) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // No item key: TMDB can credit the same person twice for different characters.
            items(items = cast) { member ->
                CastCard(cast = member)
            }
        }
    }
}

@Composable
private fun CastCard(
    cast: CastUiModel,
    modifier: Modifier = Modifier
) {
    var photoState by remember { mutableStateOf<MediaCardState>(MediaCardState.Loading) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.width(PhotoSize)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(PhotoSize)
                .clip(CircleShape)
                .background(Colors.darkGray)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cast.profilePath)
                    .crossfade(true)
                    .build(),
                onState = { state ->
                    photoState = when (state) {
                        is AsyncImagePainter.State.Success -> MediaCardState.Success
                        is AsyncImagePainter.State.Loading -> MediaCardState.Loading
                        else -> MediaCardState.Error
                    }
                },
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            when (photoState) {
                is MediaCardState.Loading -> LoadingState()
                is MediaCardState.Error -> EmptyImage()
                else -> Unit
            }
        }
        Text(
            text = cast.name,
            color = Colors.white,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = cast.character,
            color = Colors.lightGray,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun CastSectionPreview() {
    PMovieTheme {
        CastSection(
            cast = listOf(
                CastUiModel(id = 1L, name = "Pedro Pascal", character = "Joel Miller"),
                CastUiModel(id = 2L, name = "Bella Ramsey", character = "Ellie Williams"),
                CastUiModel(id = 3L, name = "Gabriel Luna", character = "Tommy Miller")
            )
        )
    }
}
