package dev.brunofelix.movies.core.presentation.ui.navigation.nav_graph

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.brunofelix.movies.core.presentation.ui.navigation.MovieRoute
import dev.brunofelix.movies.feature.details.presentation.viewmodel.MovieDetailsViewModel
import dev.brunofelix.movies.feature.favorite.presentation.ui.FavoriteScreen
import dev.brunofelix.movies.feature.favorite.presentation.viewmodel.FavoriteViewModel

fun NavGraphBuilder.favoriteScreen(
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel,
    movieDetailsViewModel: MovieDetailsViewModel
) {
    composable<MovieRoute.FavoritesScreen> {
        val movies = favoriteViewModel.uiState.collectAsState()

        FavoriteScreen(
            movies = movies.value,
            onItemClick = { movieId ->
                movieDetailsViewModel.getDetails(movieId)
                navController.navigate(MovieRoute.DetailsScreen(movieId))
            }
        )
    }
}