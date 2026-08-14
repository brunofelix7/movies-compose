package dev.brunofelix.movies.feature.search.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.components.CustomSearchBar
import dev.brunofelix.movies.core.presentation.ui.components.GradientBackground
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme

@Composable
fun MovieSearchScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    val focusManager = LocalFocusManager.current
    var searchQuery by remember { mutableStateOf("") }

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
            query = searchQuery,
            placeholderText = stringResource(R.string.search_bar_hint),
            onQueryChange = { newQuery ->
                searchQuery = newQuery
            },
            onSearch = { submittedQuery ->
                // TODO: Handle search logic here
            },
            containerColor = Color.White.copy(alpha = 0.1F),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )
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