package dev.brunofelix.movies.feature.details.presentation.ui.components

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
import dev.brunofelix.movies.core.presentation.ui.components.card.MovieCardRate
import dev.brunofelix.movies.core.presentation.ui.components.empty.EmptyData
import dev.brunofelix.movies.core.presentation.ui.resources.Colors
import dev.brunofelix.movies.feature.details.presentation.viewmodel.state.MovieDetailsUiState

@Composable
fun MovieDetailsContent(
    modifier: Modifier = Modifier,
    uiState: MovieDetailsUiState,
) {
    Column(
        modifier = modifier
            .background(Colors.blackPrimary)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (uiState) {
            is MovieDetailsUiState.Success -> {
                LazyColumn {
                    item {
                        Row {
                            MovieDetailsCoverImage(
                                uiState = uiState,
                                modifier = Modifier.weight(0.35F)
                            )
                            Column(
                                modifier = Modifier.weight(0.65F)
                            ) {
                                Column(
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Text(
                                        text = "${uiState.movie?.title}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
                                    )
                                    MovieCardRate(
                                        rate = uiState.movie?.voteAverage ?: 0F,
                                        fontSize = 14.sp,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(Modifier.size(8.dp))
                                    Text(
                                        text = "${uiState.movie?.details?.duration}min",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Colors.lightGray,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                    Spacer(Modifier.size(8.dp))
                                    Text(
                                        text = "${uiState.movie?.details?.getReleaseDate()?.value}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Colors.lightGray,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                    Spacer(Modifier.size(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        uiState.movie?.details?.genres?.forEach { category ->
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
                            text = "${uiState.movie?.details?.overview}",
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }
            is MovieDetailsUiState.Error -> {
                EmptyData(
                    message = stringResource(uiState.messageRes),
                    modifier = Modifier.padding(top = 64.dp)
                )
            }
            else -> Unit
        }
    }
}

@Preview
@Composable
private fun MovieDetailsContentPreview() {
    
}