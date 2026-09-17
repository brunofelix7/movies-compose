package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

/** Same translucency as the bottom navigation bar, so both edges of the screen match. */
private const val ScrolledContainerAlpha = 0.85F

/**
 * Top bar of the main tabs. It is transparent over the background and fades into a
 * translucent black bar once the content scrolls underneath it, which needs the caller to
 * pass the [scrollBehavior] it installed on the scaffold.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    isSearching: Boolean = false,
    onSearch: () -> Unit = {},
    onCancelSearch: () -> Unit = {},
    onSettings: () -> Unit = {}
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_title),
                color = Colors.white,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        windowInsets = TopAppBarDefaults.windowInsets.add(WindowInsets(top = 8.dp)),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Colors.blackPrimary.copy(alpha = ScrolledContainerAlpha),
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = Colors.white,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.clickable(onClick = onCancelSearch)
                        )
                        IconButton(
                            content = {
                                Icon(
                                    imageVector = Icons.Filled.SearchOff,
                                    tint = Colors.white,
                                    contentDescription = stringResource(
                                        R.string.top_bar_close_search_icon
                                    )
                                )
                            },
                            onClick = onCancelSearch
                        )
                    }
                } else {
                    Row {
                        IconButton(
                            content = {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    tint = Colors.white,
                                    contentDescription = stringResource(R.string.top_bar_search_icon)
                                )
                            },
                            onClick = onSearch
                        )
                        IconButton(
                            content = {
                                Icon(
                                    imageVector = Icons.Filled.Settings,
                                    tint = Colors.white,
                                    contentDescription = stringResource(R.string.top_bar_settings_icon)
                                )
                            },
                            onClick = onSettings
                        )
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
    MainTopBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun SearchingPreview() {
    MainTopBar(isSearching = true)
}
