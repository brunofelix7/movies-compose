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
import dev.brunofelix.movies.core.domain.model.Movie

@Composable
fun FavoriteList(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    paddingValues: PaddingValues,
    onClick: (id: Long) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
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

@Preview
@Composable
private fun FavoriteListPreview() {
    val movies = listOf(
        Movie(id = 1, title = "Movie 1", posterPath = ""),
        Movie(id = 2, title = "Movie 2", posterPath = "")
    )
    FavoriteList(
        movies = movies,
        paddingValues = PaddingValues(8.dp),
        onClick = { }
    )
}