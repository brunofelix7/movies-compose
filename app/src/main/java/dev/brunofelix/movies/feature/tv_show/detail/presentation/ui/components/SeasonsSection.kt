package dev.brunofelix.movies.feature.tv_show.detail.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.components.LoadingState
import dev.brunofelix.movies.core.presentation.ui.components.PagingRetry
import dev.brunofelix.movies.core.presentation.ui.components.SectionCard
import dev.brunofelix.movies.core.presentation.ui.model.EpisodeUiModel
import dev.brunofelix.movies.core.presentation.ui.model.SeasonUiModel
import dev.brunofelix.movies.core.presentation.ui.theme.Colors
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.feature.tv_show.detail.presentation.state.SeasonsState

private val EpisodeNumberSize = 32.dp
private val LoadingHeight = 64.dp

/**
 * Accordion of the TV show seasons. Only the expanded one shows its episodes, and they are
 * requested the first time the season is opened.
 */
@Composable
fun SeasonsSection(
    seasons: List<SeasonUiModel>,
    state: SeasonsState,
    modifier: Modifier = Modifier,
    onSeasonToggle: (Int) -> Unit = {},
    onSeasonRetry: (Int) -> Unit = {}
) {
    if (seasons.isEmpty()) return

    SectionCard(
        title = stringResource(R.string.seasons_title),
        modifier = modifier
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            seasons.forEachIndexed { index, season ->
                SeasonRow(
                    season = season,
                    isExpanded = state.expandedSeasonNumber == season.seasonNumber,
                    episodes = state.episodesOf(season.seasonNumber),
                    showDivider = index < seasons.lastIndex,
                    onToggle = { onSeasonToggle(season.seasonNumber) },
                    onRetry = { onSeasonRetry(season.seasonNumber) }
                )
            }
        }
    }
}

@Composable
private fun SeasonRow(
    season: SeasonUiModel,
    isExpanded: Boolean,
    episodes: UiState<List<EpisodeUiModel>>,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
    onToggle: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180F else 0F,
        label = "SeasonArrow"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onToggle)
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.weight(1F)) {
                Text(
                    text = season.name,
                    color = Colors.white,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(
                        R.string.season_summary,
                        season.episodeCount,
                        season.airYear
                    ),
                    color = Colors.lightGray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                tint = Colors.white,
                contentDescription = stringResource(
                    if (isExpanded) R.string.season_collapse else R.string.season_expand
                ),
                modifier = Modifier.rotate(arrowRotation)
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            when (episodes) {
                is UiState.Initial, is UiState.Loading -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LoadingHeight)
                ) {
                    LoadingState()
                }
                is UiState.Error -> PagingRetry(
                    onRetry = onRetry,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                is UiState.Empty -> Text(
                    text = stringResource(R.string.no_results_found),
                    color = Colors.lightGray,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                is UiState.Success -> Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                ) {
                    episodes.data.forEach { episode ->
                        EpisodeRow(episode = episode)
                    }
                }
            }
        }

        if (showDivider) {
            HorizontalDivider(color = Colors.white.copy(alpha = 0.1F))
        }
    }
}

@Composable
private fun EpisodeRow(
    episode: EpisodeUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(EpisodeNumberSize)
                .clip(RoundedCornerShape(8.dp))
                .background(Colors.white.copy(alpha = 0.08F))
        ) {
            Text(
                text = episode.episodeNumber.toString(),
                color = Colors.white,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = episode.name,
                color = Colors.white,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stringResource(
                    R.string.episode_summary,
                    episode.runtime,
                    episode.airDate
                ),
                color = Colors.lightGray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val seasons = listOf(
        SeasonUiModel(id = 1L, name = "Season 1", seasonNumber = 1, episodeCount = 9, airYear = "2023"),
        SeasonUiModel(id = 2L, name = "Season 2", seasonNumber = 2, episodeCount = 7, airYear = "2025")
    )

    PMovieTheme {
        SeasonsSection(
            seasons = seasons,
            state = SeasonsState(
                expandedSeasonNumber = 1,
                episodes = mapOf(
                    1 to UiState.Success(
                        listOf(
                            EpisodeUiModel(
                                id = 1L,
                                name = "When You're Lost in the Darkness",
                                episodeNumber = 1,
                                runtime = "81min",
                                airDate = "15/01/2023"
                            ),
                            EpisodeUiModel(
                                id = 2L,
                                name = "Infected",
                                episodeNumber = 2,
                                runtime = "53min",
                                airDate = "22/01/2023"
                            )
                        )
                    )
                )
            )
        )
    }
}
