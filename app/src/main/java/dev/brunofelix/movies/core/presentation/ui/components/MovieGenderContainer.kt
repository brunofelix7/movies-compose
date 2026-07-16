package dev.brunofelix.movies.core.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.core.domain.model.MovieGenre
import dev.brunofelix.movies.core.presentation.ui.theme.Colors

@Composable
fun MovieGenderContainer(
    gendersList: List<MovieGenre>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        gendersList.forEach { gender ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(24.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Colors.white.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .background(color = Colors.blackPrimary.copy(alpha = 0.4f))
            ) {
                Text(
                    text = gender.name,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
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