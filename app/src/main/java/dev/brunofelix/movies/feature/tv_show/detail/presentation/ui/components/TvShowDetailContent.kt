package dev.brunofelix.movies.feature.tv_show.detail.presentation.ui.components

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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.MovieGenre
import dev.brunofelix.movies.core.presentation.ui.components.MovieGenderContainer
import dev.brunofelix.movies.core.presentation.ui.components.MovieInfoChip
import dev.brunofelix.movies.core.presentation.ui.components.MovieOverview
import dev.brunofelix.movies.core.presentation.ui.components.SectionCard
import dev.brunofelix.movies.core.presentation.ui.components.YouTubePlayer
import dev.brunofelix.movies.core.presentation.ui.model.TvShowUiModel
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

@Composable
fun TvShowDetailContent(
    tvShow: TvShowUiModel,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.blackPrimary)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Column {
                Row {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = tvShow.name,
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
                                    text = tvShow.voteAverage
                                )
                                MovieInfoChip(
                                    icon = Icons.Outlined.CalendarMonth,
                                    text = tvShow.firstAirDate
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                MovieInfoChip(
                                    icon = Icons.Default.Layers,
                                    text = stringResource(R.string.seasons, tvShow.numberOfSeasons)
                                )
                                MovieInfoChip(
                                    icon = Icons.AutoMirrored.Filled.List,
                                    text = stringResource(R.string.episodes, tvShow.numberOfEpisodes)
                                )
                            }
                            Column(
                                modifier = Modifier.padding(vertical = 12.dp)
                            ) {
                                MovieGenderContainer(
                                    gendersList = tvShow.genres
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                MovieOverview(
                    overview = tvShow.overview,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                tvShow.trailerKey?.let { key ->
                    SectionCard(title = stringResource(R.string.trailer)) {
                        YouTubePlayer(
                            videoId = key,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    TvShowDetailContent(
        tvShow = TvShowUiModel(
            name = "The Last of Us",
            firstAirDate = "15/01/2023",
            voteAverage = "8.6",
            numberOfSeasons = 1,
            numberOfEpisodes = 9,
            overview = "Twenty years after modern civilization has been destroyed, Joel, a hardened survivor, is hired to smuggle Ellie, a 14-year-old girl, out of an oppressive quarantine zone. What starts as a small job soon becomes a brutal, heartbreaking journey, as they both must traverse the U.S. and depend on each other for survival.",
            genres = listOf(
                MovieGenre(name = "Action"),
                MovieGenre(name = "Adventure"),
                MovieGenre(name = "Drama")
            )
        )
    )
}
