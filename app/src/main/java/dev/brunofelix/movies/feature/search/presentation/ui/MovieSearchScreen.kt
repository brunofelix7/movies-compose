package dev.brunofelix.movies.feature.search.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.data.util.logInfo
import dev.brunofelix.movies.core.presentation.ui.components.CustomSearchBar
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme

@Composable
fun MovieSearchScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues()
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        CustomSearchBar(
            query = searchQuery,
            placeholderText = "Search movie",
            onQueryChange = { newQuery ->
                searchQuery = newQuery
            },
            onSearch = { submittedQuery ->
                logInfo("query: $submittedQuery")
            },
            containerColor = Color.Black.copy(alpha = 0.25F),
            modifier = Modifier
                .focusRequester(focusRequester)
                .padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    PMovieTheme {
        MovieSearchScreen()
    }
}