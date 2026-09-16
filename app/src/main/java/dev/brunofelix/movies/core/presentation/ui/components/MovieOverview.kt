package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

@Composable
fun MovieOverview(
    overview: String,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = stringResource(R.string.overview),
        modifier = modifier
    ) {
        Text(
            text = overview,
            color = Colors.white,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MovieOverview(
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    )
}
