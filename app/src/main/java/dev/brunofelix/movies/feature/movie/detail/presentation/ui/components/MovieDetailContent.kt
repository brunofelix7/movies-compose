package dev.brunofelix.movies.feature.movie.detail.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.domain.model.MovieGenre
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.MovieGenderContainer
import dev.brunofelix.movies.core.presentation.ui.components.MovieInfoChip
import dev.brunofelix.movies.core.presentation.ui.components.MovieOverview
import dev.brunofelix.movies.core.presentation.ui.model.MovieUiModel
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

@Composable
fun MovieDetailContent(
    movie: MovieUiModel,
    modifier: Modifier = Modifier
) {
    GradientBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(80.dp))
                Row {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = movie.title,
                                color = Colors.white,
                                style = MaterialTheme.typography.titleLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                                modifier = Modifier.padding(vertical = 12.dp)
                            ) {
                                MovieGenderContainer(
                                    gendersList = movie.genres
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                MovieOverview(
                    overview = movie.overview
                )
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
            )
        )
    )
}
