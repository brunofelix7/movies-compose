package dev.brunofelix.movies.feature.movie.detail.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.MediaCard
import dev.brunofelix.movies.core.presentation.ui.model.MovieUiModel

@Composable
fun MovieDetailHeader(
    movie: MovieUiModel?,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        MovieDetailTopBarImage(
            backdropPath = movie?.backdropPath
        )
        MovieDetailTopBar(
            isFavorite = isFavorite,
            shouldShowFavorite = movie != null,
            onBackClick = onBackClick,
            onFavoriteClick = onFavoriteClick
        )
        movie?.let {
            MediaCard(
                media = Media(
                    id = it.id,
                    title = it.title,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                ),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(0.45F)
                    .height(220.dp)
                    .align(Alignment.BottomStart)
                    .offset(y = 80.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Scaffold(
        topBar = {
            MovieDetailHeader(
                movie = MovieUiModel(),
                isFavorite = false,
                onBackClick = {},
                onFavoriteClick = {}
            )
        },
        content = { innerPadding ->
            GradientBackground {
                Box(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    )
}