package dev.brunofelix.movies.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.designsystem.theme.OutlineMedium
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.ScrimMedium
import dev.brunofelix.movies.designsystem.theme.shapeRounded32
import dev.brunofelix.movies.designsystem.theme.size1
import dev.brunofelix.movies.designsystem.theme.size24
import dev.brunofelix.movies.designsystem.theme.spacing8
import dev.brunofelix.movies.domain.model.MovieGenre

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieGenderContainer(
    gendersList: List<MovieGenre>,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(spacing8, Alignment.CenterHorizontally)
) {
    FlowRow(
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = Arrangement.spacedBy(spacing8),
        modifier = modifier.fillMaxWidth()
    ) {
        gendersList.forEach { gender ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(size24)
                    .clip(shapeRounded32)
                    .border(
                        border = BorderStroke(width = size1, color = OutlineMedium),
                        shape = shapeRounded32
                    )
                    .background(color = ScrimMedium)
            ) {
                Text(
                    text = gender.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = spacing8)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PMovieTheme {
        MovieGenderContainer(
            gendersList = listOf(
                MovieGenre(name = "Action"),
                MovieGenre(name = "Adventure"),
                MovieGenre(name = "Comedy"),
                MovieGenre(name = "Drama"),
                MovieGenre(name = "Terror")
            )
        )
    }
}
