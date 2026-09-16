package dev.brunofelix.movies.feature.media_list.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.domain.model.enums.MediaType
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.feature.media_list.presentation.ui.MediaListRoute

fun EntryProviderScope<NavKey>.mediaListEntry(
    onNavigate: (MainNavKey) -> Unit,
    onBack: () -> Unit
) {
    entry<MainNavKey.MediaList> { key ->
        MediaListRoute(
            category = key.category,
            month = ReleaseMonth.from(key.month),
            onItemClick = { id ->
                val route = when (key.category.mediaType) {
                    MediaType.MOVIE -> MainNavKey.MovieDetails(id)
                    MediaType.TV_SHOW -> MainNavKey.TvShowDetails(id)
                }
                onNavigate(route)
            },
            onBack = onBack
        )
    }
}
