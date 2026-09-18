package dev.brunofelix.movies.core.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.SplashGradient

/**
 * The platform draws an icon without a background inside a 288.dp box, so reusing that
 * size keeps the logo from shifting when the system splash hands over to this one.
 */
private val IconSize = 288.dp

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    GradientBackground(
        modifier = modifier,
        colors = SplashGradient
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(IconSize)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SplashScreen()
}
