package dev.brunofelix.movies.feature.tv_show.detail.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun TvShowDetailTopBarImage(
    backdropPath: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = backdropPath,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}
