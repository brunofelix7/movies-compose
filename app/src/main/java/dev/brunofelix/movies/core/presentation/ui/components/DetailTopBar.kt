package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

/** Scrim behind the icons, dark enough to keep them legible over a bright backdrop. */
private const val IconScrimAlpha = 0.45F

/**
 * Transparent top bar shared by the movie and TV show detail screens.
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
    TopAppBar(
        title = {
            if (title != null) {
                Text(
                    text = title,
                    color = Colors.white.copy(alpha = safeFraction),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium.copy(
                        fontSize = androidx.compose.ui.unit.TextUnit(
                            value = androidx.compose.material3.MaterialTheme.typography.titleMedium.fontSize.value + 2f,
                            type = androidx.compose.ui.unit.TextUnitType.Sp
                        )
                    )
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .background(
                        color = Colors.blackPrimary.copy(alpha = IconScrimAlpha * (1f - safeFraction)),
                        shape = CircleShape
                    )
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
                        .background(
                            color = Colors.blackPrimary.copy(alpha = IconScrimAlpha * (1f - safeFraction)),
                            shape = CircleShape
                        )
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
            containerColor = Colors.blackPrimary.copy(alpha = safeFraction)
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
