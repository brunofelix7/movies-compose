package dev.brunofelix.movies.feature.favorite.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.core.presentation.navigation.Navigator
import dev.brunofelix.movies.feature.favorite.presentation.state.MovieFavoriteState
import dev.brunofelix.movies.feature.favorite.presentation.ui.MovieFavoriteScreen
import dev.brunofelix.movies.feature.favorite.presentation.viewmodel.MovieFavoriteViewModel

fun EntryProviderScope<NavKey>.favoriteEntry(
    navigator: Navigator,
    paddingValues: PaddingValues
) {
    entry<MainNavKey.Favorites> {
        val viewModel: MovieFavoriteViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        MovieFavoriteScreen(
            paddingValues = paddingValues,
            state = MovieFavoriteState(
                uiState = uiState,
                onCardClick = { movieId ->
                    navigator.navigate(MainNavKey.MovieDetails(movieId))
                }
            )
        )
    }
}
