package dev.brunofelix.movies.feature.tv_show.detail.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.feature.tv_show.detail.presentation.ui.TvShowDetailRoute

fun EntryProviderScope<NavKey>.tvShowDetailEntry(
    onBack: () -> Unit
) {
    entry<MainNavKey.TvShowDetails> { key ->
        TvShowDetailRoute(
            tvShowId = key.id,
            onBack = onBack
        )
    }
}
