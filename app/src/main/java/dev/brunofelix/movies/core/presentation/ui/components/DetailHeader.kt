package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

internal val BackdropHeight = 260.dp
internal val PosterHeight = 200.dp
internal val PosterOverhang = 60.dp
internal const val PosterWidthFraction = 0.40F

/**
 * Backdrop, top bar and poster shared by the movie and TV show detail screens.
 *
 * It is meant to be used as the `topBar` of a Scaffold: the poster is pinned to the bottom
 * edge, so the scrollable content starts right below it.
 */
@Composable
fun DetailHeader(
    backdropPath: String?,
    media: Media?,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    isScrolled: Boolean = false,
    onBackClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(BackdropHeight + PosterOverhang)
            .background(Colors.blackPrimary)
    ) {
        BackdropImage(
            backdropPath = backdropPath,
            modifier = Modifier
                .fillMaxWidth()
                .height(BackdropHeight)
                .align(Alignment.TopStart)
        )
        DetailTopBar(
            isFavorite = isFavorite,
            isScrolled = isScrolled,
            shouldShowFavorite = media != null,
            onBackClick = onBackClick,
            onFavoriteClick = onFavoriteClick
        )
        media?.let {
            MediaCard(
                media = it,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(PosterWidthFraction)
                    .height(PosterHeight)
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
private fun BackdropImage(
    backdropPath: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(Colors.blackPrimary),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(backdropPath)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        if (backdropPath.isNullOrBlank()) {
            EmptyImage()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DetailHeader(
        backdropPath = "",
        media = Media(id = 1L, title = "Movie 1"),
        isFavorite = false
    )
}
