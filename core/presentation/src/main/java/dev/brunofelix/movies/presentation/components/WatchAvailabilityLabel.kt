package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Theaters
import androidx.compose.material.icons.outlined.TvOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.designsystem.theme.OutlineMedium
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.ScrimMedium
import dev.brunofelix.movies.designsystem.theme.shapeRounded32
import dev.brunofelix.movies.designsystem.theme.size1
import dev.brunofelix.movies.designsystem.theme.size20
import dev.brunofelix.movies.designsystem.theme.size32
import dev.brunofelix.movies.designsystem.theme.spacing12
import dev.brunofelix.movies.designsystem.theme.spacing8
import dev.brunofelix.movies.domain.model.WatchAvailability
import dev.brunofelix.movies.domain.model.WatchProvider

/**
 * Centred row of pills telling where a movie or TV show can be watched: one per streaming
 * service, or a single pill saying it is in theaters or not on streaming.
 *
 * The streaming services come from JustWatch through TMDB, whose terms require crediting
 * JustWatch wherever they are shown.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WatchAvailabilityLabel(
    availability: WatchAvailability,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing8),
        modifier = modifier.fillMaxWidth()
    ) {
        when (availability) {
            is WatchAvailability.Streaming -> {
                Text(
                    text = stringResource(R.string.watch_available_on),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(spacing8, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(spacing8),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    availability.providers.forEach { provider ->
                        AvailabilityPill(text = provider.name) {
                            WatchProviderLogo(logoUrl = provider.logoPath)
                        }
                    }
                }
                Text(
                    text = stringResource(R.string.watch_providers_source),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            WatchAvailability.InTheaters -> AvailabilityPill(text = stringResource(R.string.watch_in_theaters)) {
                PillIcon(icon = Icons.Outlined.Theaters, tint = MaterialTheme.colorScheme.primary)
            }
            WatchAvailability.Unavailable -> AvailabilityPill(text = stringResource(R.string.watch_unavailable)) {
                PillIcon(icon = Icons.Outlined.TvOff, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun AvailabilityPill(
    text: String,
    leading: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing8),
        modifier = Modifier
            .height(size32)
            .clip(shapeRounded32)
            .border(border = BorderStroke(width = size1, color = OutlineMedium), shape = shapeRounded32)
            .background(color = ScrimMedium)
            .padding(start = spacing8, end = spacing12)
    ) {
        leading()
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun PillIcon(
    icon: ImageVector,
    tint: Color
) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = tint,
        modifier = Modifier.size(size20)
    )
}

@Preview
@Composable
private fun StreamingPreview() {
    PMovieTheme {
        WatchAvailabilityLabel(
            availability = WatchAvailability.Streaming(
                listOf(
                    WatchProvider(id = 8L, name = "Netflix"),
                    WatchProvider(id = 119L, name = "Amazon Prime Video"),
                    WatchProvider(id = 337L, name = "Disney Plus")
                )
            )
        )
    }
}

@Preview
@Composable
private fun InTheatersPreview() {
    PMovieTheme {
        WatchAvailabilityLabel(availability = WatchAvailability.InTheaters)
    }
}

@Preview
@Composable
private fun UnavailablePreview() {
    PMovieTheme {
        WatchAvailabilityLabel(availability = WatchAvailability.Unavailable)
    }
}
