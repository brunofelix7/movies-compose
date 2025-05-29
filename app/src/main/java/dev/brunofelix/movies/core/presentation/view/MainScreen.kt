package dev.brunofelix.movies.core.presentation.view

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.brunofelix.movies.core.presentation.navigation.MovieNavHost
import dev.brunofelix.movies.core.presentation.navigation.currentRoute
import dev.brunofelix.movies.core.presentation.components.navbar.BottomNavBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute(navController)?.contains("DetailsScreen") == false) {
                BottomNavBar(navController)
            }
        },
        content = {
            MovieNavHost(navController)
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}