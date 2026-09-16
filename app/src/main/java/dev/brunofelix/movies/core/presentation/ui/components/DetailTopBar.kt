package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

/** Same translucency as the bottom navigation bar, so both edges of the screen match. */
private const val ScrolledContainerAlpha = 0.85F
private const val IconScrimAlpha = 0.2F

/**
 * Top bar shared by the movie and TV show detail screens.
 *
 * It floats transparently over the backdrop and, once [isScrolled] is true, fades into a
 * translucent black bar. The circular scrims behind the icons fade out at the same time,
 * since the bar itself then provides the contrast.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    isScrolled: Boolean = false,
    shouldShowFavorite: Boolean = true,
    onBackClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    val containerColor by animateColorAsState(
        targetValue = if (isScrolled) {
            Colors.blackPrimary.copy(alpha = ScrolledContainerAlpha)
        } else {
            Color.Transparent
        },
        label = "DetailTopBarContainer"
    )
    val iconScrimColor by animateColorAsState(
        targetValue = if (isScrolled) {
            Color.Transparent
        } else {
            Colors.blackPrimary.copy(alpha = IconScrimAlpha)
        },
        label = "DetailTopBarIconScrim"
    )

    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .background(color = iconScrimColor, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = Colors.white,
                    contentDescription = stringResource(R.string.top_bar_back_icon)
                )
            }
        },
        actions = {
            AnimatedVisibility(visible = shouldShowFavorite) {
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(color = iconScrimColor, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = if (isFavorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                        tint = if (isFavorite) Colors.redPrimary else Colors.white,
                        contentDescription = stringResource(R.string.top_bar_favorite_icon)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun Preview() {
    DetailTopBar(isFavorite = true)
}

@Preview
@Composable
private fun NotFavoritePreview() {
    DetailTopBar(isFavorite = false)
}

@Preview
@Composable
private fun ScrolledPreview() {
    DetailTopBar(isFavorite = false, isScrolled = true)
}
