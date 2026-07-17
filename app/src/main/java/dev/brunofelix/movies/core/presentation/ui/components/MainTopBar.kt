package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = title,
                color = Colors.white,
                style = MaterialTheme.typography.titleLarge
            )
        },
        windowInsets = TopAppBarDefaults.windowInsets.add(WindowInsets(top = 8.dp)),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Colors.blackPrimary.copy(alpha = 0.85F),
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        ),
        actions = {
            IconButton(
                content = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        tint = Colors.white,
                        contentDescription = stringResource(R.string.top_bar_search_icon)
                    )
                },
                onClick = {}
            )
            IconButton(
                content = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        tint = Colors.white,
                        contentDescription = stringResource(R.string.top_bar_language_icon)
                    )
                },
                onClick = {}
            )
        },
        modifier = modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun Preview() {
    MainTopBar(
        title = stringResource(R.string.upcoming),
        scrollBehavior = null
    )
}