package dev.brunofelix.movies.feature.favorite.presentation.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.ui.components.MovieTopBar
import dev.brunofelix.movies.feature.favorite.presentation.ui.components.FavoriteContent

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onItemClick: (id: Long) -> Unit
) {
    Scaffold(
        topBar = {
            MovieTopBar(
                title = stringResource(R.string.favorites)
            )
        },
        content = { innerPadding ->
            FavoriteContent(
                modifier = modifier,
                paddingValues = innerPadding,
                movies = movies,
                onClick = onItemClick
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun FavoritesScreenPreview() {

}