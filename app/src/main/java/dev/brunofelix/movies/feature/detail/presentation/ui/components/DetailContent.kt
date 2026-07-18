package dev.brunofelix.movies.feature.detail.presentation.ui.components

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
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.components.ErrorLayout
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.core.presentation.ui.components.MovieGenderContainer
import dev.brunofelix.movies.core.presentation.ui.components.MovieInfoChip
import dev.brunofelix.movies.core.presentation.ui.components.MovieOverview
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.core.util.extension.formatDecimal
import dev.brunofelix.movies.feature.detail.presentation.state.MovieDetailState

@Composable
fun DetailContent(
    state: MovieDetailState,
    modifier: Modifier = Modifier
) {
    GradientBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (state.uiState) {
                is UiState.Loading -> LoadingState()
                is UiState.Success -> {
                    Column {
                        Spacer(modifier = Modifier.height(80.dp))
                        Row {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column {
                                    Text(
                                        text = state.uiState.data.title,
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
                                            text = if (state.uiState.data.voteAverage <= 0) "--" else state.uiState.data.voteAverage.formatDecimal()
                                        )
                                        MovieInfoChip(
                                            icon = Icons.Outlined.CalendarMonth,
                                            text = state.uiState.data.releaseDate
                                        )
                                        MovieInfoChip(
                                            icon = Icons.Outlined.Timer,
                                            text = "${if (state.uiState.data.duration <= 0) "--" else state.uiState.data.duration}min"
                                        )
                                    }
                                    Column(
                                        modifier = Modifier.padding(vertical = 12.dp)
                                    ) {
                                        MovieGenderContainer(
                                            gendersList = state.uiState.data.genres
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        MovieOverview(
                            overview = state.uiState.data.overview
                        )
                    }
                }
                else -> {
                    ErrorLayout(
                        modifier = Modifier.padding(top = 64.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    DetailContent(
        state = MovieDetailState()
    )
}

@Preview
@Composable
private fun ErrorPreview() {
    DetailContent(
        state = MovieDetailState(
            uiState = UiState.Error(0)
        )
    )
}

@Preview
@Composable
private fun SuccessPreview() {
    DetailContent(
        state = MovieDetailState(
            uiState = UiState.Success(
                data = MovieUiState(
                    title = "Super Mario Galaxy",
                    releaseDate = "01/04/2026",
                    duration = 120,
                    voteAverage = 7.5F,
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
        )
    )
}
