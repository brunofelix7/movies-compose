package dev.brunofelix.movies.feature.movie.presentation.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.core.presentation.components.EmptyData
import dev.brunofelix.movies.core.presentation.resources.Colors
import dev.brunofelix.movies.feature.movie.presentation.components.MovieRate
import dev.brunofelix.movies.feature.movie.presentation.state.MovieDetailsUiState

@Composable
fun MovieDetailsContent(
    modifier: Modifier = Modifier,
    uiState: MovieDetailsUiState?
) {
    Column(
        modifier = modifier
            .background(Colors.blackPrimary)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        uiState?.let {
            when (it) {
                is MovieDetailsUiState.Loading -> {

                }
                is MovieDetailsUiState.Success -> {
                    LazyColumn {
                        item {
                            Row {
                                MovieDetailsCoverImage(
                                    uiState = it,
                                    modifier = Modifier.weight(0.35F)
                                )
                                Column(
                                    modifier = Modifier.weight(0.65F)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(start = 8.dp)
                                    ) {
                                        Text(
                                            text = "${it.movie?.title}",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
                                        )
                                        MovieRate(
                                            rate = it.movie?.voteAverage ?: 0F,
                                            fontSize = 14.sp,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(Modifier.size(8.dp))
                                        Text(
                                            text = "${it.movie?.details?.duration}min",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Colors.lightGray,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                        Spacer(Modifier.size(8.dp))
                                        Text(
                                            text = "${it.movie?.details?.releaseDate}",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Colors.lightGray,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                        Spacer(Modifier.size(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            it.movie?.details?.genres?.forEach { category ->
                                                Text(
                                                    text = category.name,
                                                    fontSize = 14.sp,
                                                    color = Colors.lightGray,
                                                    fontWeight = FontWeight.SemiBold,
                                                    modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            Text(
                                text = "Overview",
                                color = Colors.lightGray,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 4.dp, top = 16.dp)
                            )
                            Text(
                                text = "${it.movie?.details?.overview}",
                                fontWeight = FontWeight.Light,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }
                }
                is MovieDetailsUiState.Error -> {
                    EmptyData(
                        message = stringResource(it.messageRes),
                        modifier = Modifier.padding(top = 64.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MovieDetailsContentPreview() {
    
}