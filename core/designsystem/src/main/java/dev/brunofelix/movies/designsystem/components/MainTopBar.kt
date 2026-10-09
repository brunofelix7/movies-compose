package dev.brunofelix.movies.designsystem.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.designsystem.R
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.ScrimBar
import dev.brunofelix.movies.designsystem.theme.shapeCircle
import dev.brunofelix.movies.designsystem.theme.spacing16
import dev.brunofelix.movies.designsystem.theme.spacing8

/**
 * Top bar of the main tabs. It is transparent over the background and fades into the same
 * translucent black as the bottom navigation bar once the content scrolls underneath it, which
 * needs the caller to pass the [scrollBehavior] it installed on the scaffold.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    isSearching: Boolean = false,
    onSearch: () -> Unit = {},
    onCancelSearch: () -> Unit = {},
    onSettings: () -> Unit = {}
) {
    val contentColor = MaterialTheme.colorScheme.onBackground

    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = title,
                color = contentColor,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        windowInsets = TopAppBarDefaults.windowInsets.add(WindowInsets(top = spacing8)),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = ScrimBar,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        ),
        actions = {
            AnimatedContent(
                targetState = isSearching,
                contentAlignment = Alignment.CenterEnd,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut() using SizeTransform(clip = false)
                },
                label = "TopBarSearchActionTransition"
            ) { searching ->
                if (searching) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(spacing8),
                        modifier = Modifier
                            // Before the click, otherwise the ripple stays square.
                            .clip(shapeCircle)
                            .clickable(onClick = onCancelSearch)
                            .padding(horizontal = spacing16, vertical = spacing8)
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = contentColor,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Icon(
                            imageVector = Icons.Filled.SearchOff,
                            tint = contentColor,
                            contentDescription = stringResource(R.string.top_bar_close_search_icon)
                        )
                    }
                } else {
                    Row {
                        IconButton(onClick = onSearch) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                tint = contentColor,
                                contentDescription = stringResource(R.string.top_bar_search_icon)
                            )
                        }
                        IconButton(onClick = onSettings) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                tint = contentColor,
                                contentDescription = stringResource(R.string.top_bar_settings_icon)
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        MainTopBar(title = "Movies Explorer")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun SearchingPreview() {
    PMovieTheme {
        MainTopBar(title = "Movies Explorer", isSearching = true)
    }
}
