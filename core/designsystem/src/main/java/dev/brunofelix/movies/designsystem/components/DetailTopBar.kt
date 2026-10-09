package dev.brunofelix.movies.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.designsystem.R
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.shapeCircle
import dev.brunofelix.movies.designsystem.theme.spacing8

/** Scrim behind the icons, dark enough to keep them legible over a bright backdrop. */
private const val ICON_SCRIM_ALPHA = 0.45f

/**
 * Transparent top bar shared by the movie and TV show detail screens. The [title] fades in and
 * the bar turns solid as [scrollFraction] goes from 0 to 1.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    shouldShowFavorite: Boolean = true,
    title: String? = null,
    scrollFraction: Float = 0f,
    onBackClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    val safeFraction = scrollFraction.coerceIn(0f, 1f)
    val iconScrim = MaterialTheme.colorScheme.scrim.copy(alpha = ICON_SCRIM_ALPHA * (1f - safeFraction))
    val onBackground = MaterialTheme.colorScheme.onBackground

    TopAppBar(
        title = {
            if (title != null) {
                Text(
                    text = title,
                    color = onBackground.copy(alpha = safeFraction),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(start = spacing8)
                    .background(color = iconScrim, shape = shapeCircle)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = onBackground,
                    contentDescription = stringResource(R.string.top_bar_back_icon)
                )
            }
        },
        actions = {
            AnimatedVisibility(visible = shouldShowFavorite) {
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .padding(end = spacing8)
                        .background(color = iconScrim, shape = shapeCircle)
                ) {
                    Icon(
                        imageVector = if (isFavorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else onBackground,
                        contentDescription = stringResource(R.string.top_bar_favorite_icon)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = safeFraction)
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun FavoritePreview() {
    PMovieTheme {
        DetailTopBar(isFavorite = true)
    }
}

@Preview
@Composable
private fun NotFavoritePreview() {
    PMovieTheme {
        DetailTopBar(isFavorite = false)
    }
}

@Preview
@Composable
private fun ScrolledPreview() {
    PMovieTheme {
        DetailTopBar(isFavorite = false, title = "Dune: Part Two", scrollFraction = 1f)
    }
}
