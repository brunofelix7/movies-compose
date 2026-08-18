package dev.brunofelix.movies.feature.search.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.enums.MediaType
import dev.brunofelix.movies.core.presentation.navigation.MainNavKey
import dev.brunofelix.movies.core.presentation.ui.components.CustomSearchBar
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.components.MediaCard
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme
import dev.brunofelix.movies.feature.search.presentation.viewmodel.SearchViewModel

@Composable
fun MovieSearchScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigate: (MainNavKey) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchResults = state.searchResults.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        CustomSearchBar(
            query = state.query,
            placeholderText = stringResource(R.string.search_bar_hint),
            onQueryChange = viewModel::onQueryChange,
            containerColor = Color.White.copy(alpha = 0.1F),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            onSearch = {
                viewModel.onSearch()
                focusManager.clearFocus()
            }
        )
        val isSearching = state.isLoading || searchResults.loadState.refresh is LoadState.Loading
        if (state.query.isNotBlank() && isSearching) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.CenterHorizontally
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize().padding(top = 16.dp)
        ) {
            items(
                count = searchResults.itemCount,
                key = searchResults.itemKey { it.id }
            ) { index ->
                searchResults[index]?.let { media ->
                    MediaCard(
                        media = media,
                        onClick = {
                            val route = when (media.type) {
                                MediaType.MOVIE -> MainNavKey.MovieDetails(media.id)
                                MediaType.TV_SHOW -> MainNavKey.TvShowDetails(media.id)
                            }
                            onNavigate(route)
                        }
                    )
                }
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    PMovieTheme {
        GradientBackground {
            MovieSearchScreen()
        }
    }
}