package dev.brunofelix.movies.presentation.detail.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.brunofelix.movies.designsystem.components.MovieInfoChip
import dev.brunofelix.movies.designsystem.components.SectionCard
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.RatingStar
import dev.brunofelix.movies.designsystem.theme.spacing100
import dev.brunofelix.movies.designsystem.theme.spacing12
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing8
import dev.brunofelix.movies.domain.model.MovieGenre
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.components.CastSection
import dev.brunofelix.movies.presentation.components.MovieGenderContainer
import dev.brunofelix.movies.presentation.components.MovieOverview
import dev.brunofelix.movies.presentation.components.YouTubePlayer
import dev.brunofelix.movies.presentation.model.CastUiModel
import dev.brunofelix.movies.presentation.model.MovieUiModel

private const val BACKDROP_SCREEN_FRACTION = 0.65f
private const val BACKDROP_VISIBLE_FRACTION = 0.6f
private const val BACKDROP_FADE_FRACTION = 0.4f
private const val PARALLAX_FACTOR = 0.5f

/**
 * Scrolling body of the movie details: a parallax backdrop that fades out under the
 * information, overview, trailer and cast sections.
 */
@Composable
internal fun MovieDetailContent(
    movie: MovieUiModel,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    val imageHeight = LocalConfiguration.current.screenHeightDp.dp * BACKDROP_SCREEN_FRACTION
    val background = MaterialTheme.colorScheme.background

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.posterPath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .graphicsLayer {
                    val collapseRange = imageHeight.toPx()
                    val collapseFraction = (scrollState.value / collapseRange).coerceIn(0f, 1f)
                    alpha = 1f - collapseFraction
                    translationY = -scrollState.value * PARALLAX_FACTOR
                }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(imageHeight * BACKDROP_VISIBLE_FRACTION))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight * BACKDROP_FADE_FRACTION)
                    .background(Brush.verticalGradient(colors = listOf(Color.Transparent, background)))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background)
                    .padding(horizontal = spacing16)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(spacing12, Alignment.CenterHorizontally),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MovieInfoChip(
                            icon = Icons.Default.Star,
                            iconTint = RatingStar,
                            text = movie.voteAverage
                        )
                        MovieInfoChip(
                            icon = Icons.Outlined.CalendarMonth,
                            text = movie.releaseDate
                        )
                        MovieInfoChip(
                            icon = Icons.Outlined.Timer,
                            text = movie.duration
                        )
                    }
                    Column(
                        modifier = Modifier.padding(vertical = spacing12),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MovieGenderContainer(gendersList = movie.genres)
                    }
                }
                Spacer(modifier = Modifier.height(spacing8))

                MovieOverview(
                    overview = movie.overview,
                    modifier = Modifier.padding(bottom = spacing16)
                )

                movie.trailerKey?.let { key ->
                    SectionCard(
                        title = stringResource(R.string.trailer),
                        modifier = Modifier.padding(bottom = spacing16)
                    ) {
                        YouTubePlayer(
                            videoId = key,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                CastSection(cast = movie.cast)

                Spacer(modifier = Modifier.height(spacing100))
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        MovieDetailContent(
            movie = MovieUiModel(
                title = "Super Mario Galaxy",
                releaseDate = "01/04/2026",
                duration = "2h",
                voteAverage = "7.3",
                overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.",
                trailerKey = "Way9Dexny3w",
                genres = listOf(
                    MovieGenre(name = "Action"),
                    MovieGenre(name = "Adventure"),
                    MovieGenre(name = "Comedy")
                ),
                cast = listOf(
                    CastUiModel(id = 1L, name = "Chris Pratt", character = "Mario"),
                    CastUiModel(id = 2L, name = "Anya Taylor-Joy", character = "Princess Peach")
                )
            )
        )
    }
}
