package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    paddingValues: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier.padding(top = 12.dp),
        navController = navController,
        startDestination = AppDestination.Movies
    ) {
        popularNavGraph(
            navController = navController,
            paddingValues = paddingValues
        )
        upcomingNavGraph(
            navController = navController,
            paddingValues = paddingValues
        )
        composable<AppDestination.Search> {
            MovieSearchScreen(
                paddingValues = paddingValues
            )
        }
        movieDetailGraph(
            navController = navController
        )
        favoriteGraph(
            navController = navController,
            paddingValues = paddingValues
        )
    }
}
