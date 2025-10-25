package dev.brunofelix.movies.feature.favorite.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    movies: List<Movie>,
    onClick: (id: Long) -> Unit
) {
    Box(
        modifier = modifier.background(Colors.blackPrimary)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = paddingValues,
            content = {
                items(
                    items = movies,
                    key = { item: Movie -> item.id }
                ) { movie ->
                    FavoriteItem (
                        movie = movie,
                        onClick = {
                            onClick(movie.id)
                        }
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteContentPreview() {

}