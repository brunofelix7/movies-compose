package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.components.SelectorChip
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing8
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.feature.favorite.R

private const val ALL_PROVIDERS_KEY = "all"

/**
 * Horizontal streaming filter of the favorites: an "All" chip followed by one chip per service.
 * Tapping the selected service clears the filter.
 */
@Composable
internal fun WatchProviderSelector(
    providers: List<WatchProvider>,
    selectedProviderId: Long?,
    modifier: Modifier = Modifier,
    onProviderSelected: (Long?) -> Unit = {}
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = spacing16),
        horizontalArrangement = Arrangement.spacedBy(spacing8),
        modifier = modifier.fillMaxWidth()
    ) {
        item(key = ALL_PROVIDERS_KEY) {
            SelectorChip(
                label = stringResource(R.string.favorites_all_providers),
                isSelected = selectedProviderId == null,
                onClick = { onProviderSelected(null) }
            )
        }
        items(items = providers, key = { it.id }) { provider ->
            val isSelected = provider.id == selectedProviderId
            SelectorChip(
                label = provider.name,
                isSelected = isSelected,
                onClick = { onProviderSelected(provider.id.takeUnless { isSelected }) },
                leadingIcon = { WatchProviderLogo(logoUrl = provider.logoPath) }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        WatchProviderSelector(
            providers = listOf(
                WatchProvider(id = 8L, name = "Netflix"),
                WatchProvider(id = 119L, name = "Prime Video"),
                WatchProvider(id = 1899L, name = "Max")
            ),
            selectedProviderId = 8L
        )
    }
}
