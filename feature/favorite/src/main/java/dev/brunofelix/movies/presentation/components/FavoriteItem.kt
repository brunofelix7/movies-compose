package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.brunofelix.movies.designsystem.components.MovieInfoChip
import dev.brunofelix.movies.designsystem.theme.IconSilver
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.RatingStar
import dev.brunofelix.movies.designsystem.theme.elevation4
import dev.brunofelix.movies.designsystem.theme.shapeRounded12
import dev.brunofelix.movies.designsystem.theme.size100
import dev.brunofelix.movies.designsystem.theme.spacing12
import dev.brunofelix.movies.designsystem.theme.spacing2
import dev.brunofelix.movies.designsystem.theme.spacing4
import dev.brunofelix.movies.designsystem.theme.spacing8
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.presentation.model.MediaUiModel

private const val POSTER_WEIGHT = 0.3f
private const val INFO_WEIGHT = 0.7f
private const val TITLE_MAX_LINES = 2

/**
 * Row of the favorites list: poster, title and the release, duration and rating chips.
 */
@Composable
internal fun FavoriteItem(
    media: MediaUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = elevation4),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        shape = shapeRounded12,
        modifier = modifier
            .fillMaxWidth()
            .height(size100)
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing12)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(media.posterPath)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.weight(POSTER_WEIGHT)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(spacing8),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(INFO_WEIGHT)
                    .padding(vertical = spacing4)
            ) {
                Text(
                    text = media.title,
                    maxLines = TITLE_MAX_LINES,
                    style = MaterialTheme.typography.labelMedium,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing8)
                ) {
                    MovieInfoChip(
                        icon = Icons.Outlined.CalendarMonth,
                        text = media.releaseDate
                    )
                    if (media.type == MediaType.MOVIE) {
                        MovieInfoChip(
                            icon = Icons.Outlined.Timer,
                            text = media.duration
                        )
                    }
                }
                MovieInfoChip(
                    icon = Icons.Default.Star,
                    iconTint = RatingStar,
                    text = media.voteAverage
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = IconSilver
            )
            Spacer(Modifier.width(spacing2))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviePreview() {
    PMovieTheme {
        FavoriteItem(
            media = MediaUiModel(
                id = 1,
                title = "Dune: Part Two",
                releaseDate = "27/02/2024",
                duration = "166min",
                voteAverage = "8.2",
                type = MediaType.MOVIE
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TvShowPreview() {
    PMovieTheme {
        FavoriteItem(
            media = MediaUiModel(
                id = 2,
                title = "Dark",
                releaseDate = "01/12/2017",
                voteAverage = "8.4",
                type = MediaType.TV_SHOW
            )
        )
    }
}
