package dev.brunofelix.movies.feature.favorite.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.domain.model.Media

@Composable
fun MovieFavoriteList(
    modifier: Modifier = Modifier,
    medias: List<Media>,
    paddingValues: PaddingValues,
    onClick: (id: Long) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = paddingValues,
        content = {
            items(
                items = medias,
                key = { item: Media -> item.id }
            ) { media ->
                FavoriteItem (
                    media = media,
                    onClick = {
                        onClick(media.id)
                    }
                )
            }
        }
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    val medias = listOf(
        Media(id = 1, title = "Movie 1", posterPath = ""),
        Media(id = 2, title = "Movie 2", posterPath = "")
    )
    MovieFavoriteList(
        medias = medias,
        paddingValues = PaddingValues(8.dp),
        onClick = { }
    )
}