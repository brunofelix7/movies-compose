package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.components.SectionCard
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.core.presentation.R

@Composable
fun MovieOverview(
    overview: String,
    modifier: Modifier = Modifier
) {
    SectionCard(
        title = stringResource(R.string.overview),
        modifier = modifier
    ) {
        Column {
            Text(
                text = overview,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        MovieOverview(
            overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo."
        )
    }
}

@Preview
@Composable
private fun ShortPreview() {
    PMovieTheme {
        MovieOverview(overview = "Lorem ipsum dolor sit amet.")
    }
}
