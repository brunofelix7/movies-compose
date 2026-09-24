package dev.brunofelix.movies.feature.movie.detail.presentation.ui.components

import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Brush
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.MovieGenre
import dev.brunofelix.movies.core.presentation.ui.components.CastSection
import dev.brunofelix.movies.core.presentation.ui.components.MovieGenderContainer
import dev.brunofelix.movies.core.presentation.ui.components.MovieInfoChip
import dev.brunofelix.movies.core.presentation.ui.components.MovieOverview
import dev.brunofelix.movies.core.presentation.ui.components.SectionCard
import dev.brunofelix.movies.core.presentation.ui.components.YouTubePlayer
import dev.brunofelix.movies.core.presentation.ui.model.CastUiModel
import dev.brunofelix.movies.core.presentation.ui.model.MovieUiModel
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

@Composable
fun MovieDetailContent(
    movie: MovieUiModel,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val imageHeight = screenHeight * 0.75f

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Colors.blackPrimary)
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
                    translationY = scrollState.value * 0.5f
                }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(imageHeight * 0.6f))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight * 0.4f)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Colors.blackPrimary)
                        )
                    )
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.blackPrimary)
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MovieInfoChip(
                            icon = Icons.Default.Star,
                            iconTint = Color.Yellow,
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
                        modifier = Modifier.padding(vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MovieGenderContainer(
                            gendersList = movie.genres
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                movie.trailerKey?.let { key ->
                    SectionCard(
                        title = stringResource(R.string.trailer),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        YouTubePlayer(
                            videoId = key,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                MovieOverview(
                    overview = movie.overview,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CastSection(cast = movie.cast)

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    MovieDetailContent(
        movie = MovieUiModel(
            title = "Super Mario Galaxy",
            releaseDate = "01/04/2026",
            duration = "120min",
            voteAverage = "7.3",
            overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            genres = listOf(
                MovieGenre(name = "Action"),
                MovieGenre(name = "Adventure"),
                MovieGenre(name = "Comedy"),
                MovieGenre(name = "Drama"),
                MovieGenre(name = "Terror")
            ),
            cast = listOf(
                CastUiModel(id = 1L, name = "Chris Pratt", character = "Mario"),
                CastUiModel(id = 2L, name = "Anya Taylor-Joy", character = "Princess Peach"),
                CastUiModel(id = 3L, name = "Jack Black", character = "Bowser")
            )
        )
    )
}

