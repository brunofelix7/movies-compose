package dev.brunofelix.movies.feature.popular.presentation.viewmodel.state

import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MoviePopularUiState(
    val movies: Flow<PagingData<Movie>> = emptyFlow()
)