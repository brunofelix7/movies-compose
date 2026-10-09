package dev.brunofelix.movies.presentation.detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import dev.brunofelix.movies.designsystem.components.EmptyImage
import dev.brunofelix.movies.designsystem.components.LoadingState
import dev.brunofelix.movies.designsystem.components.PagingRetry
import dev.brunofelix.movies.designsystem.components.SectionCard
import dev.brunofelix.movies.designsystem.theme.OutlineSubtle
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.ScrimHeavy
import dev.brunofelix.movies.designsystem.theme.shapeRounded12
import dev.brunofelix.movies.designsystem.theme.shapeRounded8
import dev.brunofelix.movies.designsystem.theme.size160
import dev.brunofelix.movies.designsystem.theme.size64
import dev.brunofelix.movies.designsystem.theme.spacing12
import dev.brunofelix.movies.designsystem.theme.spacing4
import dev.brunofelix.movies.designsystem.theme.spacing8
import dev.brunofelix.movies.presentation.model.EpisodeUiModel
import dev.brunofelix.movies.presentation.model.SeasonUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.feature.tv_show.R
import dev.brunofelix.movies.presentation.detail.SeasonsState
import dev.brunofelix.movies.core.designsystem.R as DesignSystemR

private const val ARROW_EXPANDED_DEGREES = 180f
private const val STILL_GRADIENT_START = 0.4f
private const val STILL_GRADIENT_END = 1f

/**
 * Accordion of the TV show seasons. Only the expanded one shows its episodes, and they are
 * requested the first time the season is opened.
 */
@Composable
internal fun SeasonsSection(
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
        Column(verticalArrangement = Arrangement.spacedBy(spacing4)) {
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
        targetValue = if (isExpanded) ARROW_EXPANDED_DEGREES else 0f,
        label = "SeasonArrow"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapeRounded8)
                .clickable(onClick = onToggle)
                .padding(vertical = spacing8)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = season.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.season_summary, season.episodeCount, season.airYear),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                tint = MaterialTheme.colorScheme.onBackground,
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
                        .height(size64)
                ) {
                    LoadingState()
                }
                is UiState.Error -> PagingRetry(
                    onRetry = onRetry,
                    modifier = Modifier.padding(vertical = spacing8)
                )
                is UiState.Empty -> Text(
                    text = stringResource(DesignSystemR.string.no_results_found),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = spacing8)
                )
                is UiState.Success -> LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(spacing12),
                    contentPadding = PaddingValues(bottom = spacing12),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(episodes.data, key = { it.id }) { episode ->
                        EpisodeCard(episode = episode)
                    }
                }
            }
        }

        if (showDivider) {
            HorizontalDivider(color = OutlineSubtle)
        }
    }
}

@Composable
private fun EpisodeCard(
    episode: EpisodeUiModel,
    modifier: Modifier = Modifier
) {
    var imageError by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(size160)
            .clip(shapeRounded12)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(episode.stillPath)
                .crossfade(true)
                .build(),
            onState = { state ->
                imageError = state is AsyncImagePainter.State.Error || state is AsyncImagePainter.State.Empty
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (imageError || episode.stillPath.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                EmptyImage()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        STILL_GRADIENT_START to Color.Transparent,
                        STILL_GRADIENT_END to ScrimHeavy
                    )
                )
        )

        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing8)
        ) {
            Text(
                text = stringResource(R.string.episode_number, episode.episodeNumber),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = episode.name,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stringResource(R.string.episode_summary, episode.runtime, episode.airDate),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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

@Preview
@Composable
private fun LoadingPreview() {
    PMovieTheme {
        SeasonsSection(
            seasons = listOf(SeasonUiModel(id = 1L, name = "Season 1", seasonNumber = 1, episodeCount = 9, airYear = "2023")),
            state = SeasonsState(expandedSeasonNumber = 1, episodes = mapOf(1 to UiState.Loading))
        )
    }
}
