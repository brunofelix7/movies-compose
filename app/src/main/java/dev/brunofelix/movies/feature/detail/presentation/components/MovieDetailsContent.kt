package dev.brunofelix.movies.feature.detail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.components.EmptyState
import dev.brunofelix.movies.core.presentation.components.LoadingState
import dev.brunofelix.movies.core.presentation.components.MovieRate
import dev.brunofelix.movies.core.presentation.components.CustomButton
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.presentation.state.UiState
import dev.brunofelix.movies.core.presentation.ui.resources.Colors

@Composable
fun MovieDetailsContent(
    modifier: Modifier = Modifier,
    uiState: UiState<MovieUiState>,
    onHideVoteAverage: () -> Unit
) {
    Column(
        modifier = modifier
            .background(Colors.blackPrimary)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (uiState) {
            is UiState.Loading -> LoadingState()
            is UiState.Success -> {
                onHideVoteAverage()
                LazyColumn {
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                    item {
                        Row {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column {
                                    Text(
                                        text = uiState.data.title,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = uiState.data.releaseDate,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Colors.lightGray,
                                            modifier = Modifier.padding()
                                        )
                                        Text(
                                            text = "|",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Colors.lightGray,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                        Text(
                                            text = "${uiState.data.duration}min",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Colors.lightGray,
                                            modifier = Modifier.padding()
                                        )
                                    }
                                    Spacer(Modifier.size(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        uiState.data.genres.forEach { category ->
                                            Text(
                                                text = category.name,
                                                fontSize = 14.sp,
                                                color = Colors.lightGray,
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier.padding(bottom = 8.dp)
                                            )
                                            Spacer(Modifier.size(8.dp))
                                        }
                                    }
                                    Spacer(Modifier.size(8.dp))
                                    MovieRate(
                                        rate = uiState.data.voteAverage,
                                        fontSize = 14.sp,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.size(16.dp))
                        CustomButton(
                            onClick = {
                                // TODO: Handle click on trailer button
                            }
                        )
                        Text(
                            text = "Overview",
                            color = Colors.lightGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 4.dp, top = 16.dp)
                        )
                        Text(
                            text = uiState.data.overview,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }
            is UiState.Empty -> {
                EmptyState(
                    message = stringResource(R.string.empty_state),
                    modifier = Modifier.padding(top = 64.dp)
                )
            }
            is UiState.Error -> {
                EmptyState(
                    message = stringResource(uiState.messageRes),
                    modifier = Modifier.padding(top = 64.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MovieDetailsContentPreview() {
    MovieDetailsContent(
        uiState = UiState.Loading,
        onHideVoteAverage = {}
    )
}