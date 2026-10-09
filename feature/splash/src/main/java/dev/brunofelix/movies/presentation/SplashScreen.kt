package dev.brunofelix.movies.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.components.GradientBackground
import dev.brunofelix.movies.designsystem.components.SplashGradient
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.size288
import kotlinx.coroutines.delay

const val SPLASH_SCREEN_TEST_TAG = "splash_screen"

/** How long the splash stays on screen before handing over to the first tab. */
internal const val SPLASH_HOLD_MILLIS = 900L

@Composable
internal fun SplashRoute(
    @DrawableRes logoRes: Int,
    onFinished: () -> Unit
) {
    val currentOnFinished by rememberUpdatedState(onFinished)

    LaunchedEffect(Unit) {
        delay(SPLASH_HOLD_MILLIS)
        currentOnFinished()
    }

    SplashScreen(logoRes = logoRes)
}

/**
 * The platform draws an icon without a background inside a 288dp box, so reusing that size
 * keeps the logo from shifting when the system splash hands over to this one.
 */
@Composable
internal fun SplashScreen(
    @DrawableRes logoRes: Int,
    modifier: Modifier = Modifier
) {
    GradientBackground(
        modifier = modifier.testTag(SPLASH_SCREEN_TEST_TAG),
        colors = SplashGradient
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(logoRes),
                contentDescription = null,
                modifier = Modifier.size(size288)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        SplashScreen(logoRes = android.R.drawable.star_big_on)
    }
}
