package dev.brunofelix.movies.feature.release.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.core.presentation.navigation.toDetailNavKey
import dev.brunofelix.movies.feature.release.presentation.ui.ReleaseScreen

fun EntryProviderScope<NavKey>.releaseEntry(
    onNavigate: (MainNavKey) -> Unit,
    paddingValues: PaddingValues
) {
    entry<MainNavKey.Releases> {
        ReleaseScreen(
            onItemClick = { media -> onNavigate(media.toDetailNavKey()) },
            onViewMore = { category, month ->
                onNavigate(MainNavKey.MediaList(category = category, month = month.id))
            },
            paddingValues = paddingValues
        )
    }
}
