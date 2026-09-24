package dev.brunofelix.movies.feature.tv_show.detail.presentation.ui.components

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
import dev.brunofelix.movies.core.presentation.ui.components.CastSection
import dev.brunofelix.movies.core.presentation.ui.components.MovieGenderContainer
import dev.brunofelix.movies.core.presentation.ui.components.MovieInfoChip
import dev.brunofelix.movies.core.presentation.ui.components.MovieOverview
import dev.brunofelix.movies.core.presentation.ui.components.SectionCard
import dev.brunofelix.movies.core.presentation.ui.components.YouTubePlayer
import dev.brunofelix.movies.core.presentation.ui.model.CastUiModel
import dev.brunofelix.movies.core.presentation.ui.model.SeasonUiModel
import dev.brunofelix.movies.core.presentation.ui.model.TvShowUiModel
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.feature.tv_show.detail.presentation.state.SeasonsState

@Composable
fun TvShowDetailContent(
    tvShow: TvShowUiModel,
    seasonsState: SeasonsState,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    onSeasonToggle: (Int) -> Unit = {},
    onSeasonRetry: (Int) -> Unit = {}
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
                .data(tvShow.posterPath)
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

                tvShow.trailerKey?.let { key ->
                    SectionCard(
                        title = stringResource(R.string.trailer),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        YouTubePlayer(
                            videoId = key,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                }

                MovieOverview(
                    overview = tvShow.overview,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CastSection(
                    cast = tvShow.cast,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                SeasonsSection(
                    seasons = tvShow.seasons,
                    state = seasonsState,
                    onSeasonToggle = onSeasonToggle,
                    onSeasonRetry = onSeasonRetry
                )

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
            ),
            seasons = listOf(
                SeasonUiModel(
                    id = 1L,
                    name = "Season 1",
                    seasonNumber = 1,
                    episodeCount = 9,
                    airYear = "2023"
                )
            ),
            cast = listOf(
                CastUiModel(id = 1L, name = "Pedro Pascal", character = "Joel Miller"),
                CastUiModel(id = 2L, name = "Bella Ramsey", character = "Ellie Williams"),
                CastUiModel(id = 3L, name = "Gabriel Luna", character = "Tommy Miller")
            )
        ),
        seasonsState = SeasonsState()
    )
}

