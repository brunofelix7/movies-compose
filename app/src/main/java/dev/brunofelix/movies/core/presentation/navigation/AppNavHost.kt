package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.brunofelix.movies.feature.detail.presentation.navigation.movieDetailGraph
import dev.brunofelix.movies.feature.favorite.presentation.navigation.favoriteGraph
import dev.brunofelix.movies.feature.popular.presentation.navigation.popularNavGraph
import dev.brunofelix.movies.feature.search.presentation.ui.MovieSearchScreen
import dev.brunofelix.movies.feature.upcoming.presentation.navigation.upcomingNavGraph

@Composable
fun AppNavHost(
    innerPadding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppDestination.Movies
    ) {
        popularNavGraph(
            navController = navController
        )
        upcomingNavGraph(
            navController = navController
        )
        composable<AppDestination.Search> { backStackEntry ->
            MovieSearchScreen()
        }
        movieDetailGraph(
            navController = navController
        )
        favoriteGraph(
            navController = navController
        )
    }
}