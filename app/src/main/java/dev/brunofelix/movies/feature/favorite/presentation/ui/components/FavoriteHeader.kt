package dev.brunofelix.movies.feature.favorite.presentation.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.components.MainTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteHeader(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    MainTopBar(
        title = stringResource(R.string.favorites),
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun Preview() {
    FavoriteHeader(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        modifier = Modifier
    )
}
