package dev.brunofelix.movies.core.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.brunofelix.movies.feature.detail.presentation.navigation.movieDetailGraph
import dev.brunofelix.movies.feature.favorite.presentation.navigation.favoriteNavGraph
import dev.brunofelix.movies.feature.popular.presentation.navigation.popularNavGraph
import dev.brunofelix.movies.feature.upcoming.presentation.navigation.upcomingNavGraph

@Composable
fun MovieNavHost(
    innerPadding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MovieDestination.Populars
    ) {
        // Popular Graph
        popularNavGraph(
            navController = navController
        )

        // Upcoming Graph
        upcomingNavGraph(
            navController = navController
        )

        // Movie Detail Graph
        movieDetailGraph(
            navController = navController
        )

        // Favorite Graph
        favoriteNavGraph(
            navController = navController
        )
    }
}