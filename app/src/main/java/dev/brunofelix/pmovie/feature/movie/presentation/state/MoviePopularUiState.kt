package dev.brunofelix.pmovie.feature.movie.presentation.state

import androidx.paging.PagingData
import dev.brunofelix.pmovie.feature.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MoviePopularUiState(
    val movies: Flow<PagingData<Movie>> = emptyFlow()
)