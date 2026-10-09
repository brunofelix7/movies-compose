package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.shapeRounded4
import dev.brunofelix.movies.designsystem.theme.size20

/**
 * Square logo of a streaming service. It is decorative: callers always show the service name
 * next to it.
 */
@Composable
fun WatchProviderLogo(
    logoUrl: String,
    modifier: Modifier = Modifier,
    size: Dp = size20
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(logoUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size)
            .clip(shapeRounded4)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        WatchProviderLogo(logoUrl = "")
    }
}
