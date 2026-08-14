package dev.brunofelix.movies.feature.movie.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.model.enums.MovieCategory
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetPopularUseCase
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetTopRatedUseCase
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetUpcomingUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MovieHomeViewModel @Inject constructor(
    private val getPopularUseCase: GetPopularUseCase,
    private val getUpcomingUseCase: GetUpcomingUseCase,
    private val getTopRatedUseCase: GetTopRatedUseCase
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(MovieCategory.POPULAR)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val popularMoviesFlow = getPopularUseCase().cachedIn(viewModelScope)
    private val upcomingMoviesFlow = getUpcomingUseCase().cachedIn(viewModelScope)
    private val topRatedMoviesFlow = getTopRatedUseCase().cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<Movie>> = _selectedCategory.flatMapLatest { category ->
        when (category) {
            MovieCategory.POPULAR -> popularMoviesFlow
            MovieCategory.UPCOMING -> upcomingMoviesFlow
            MovieCategory.TOP_RATED -> topRatedMoviesFlow
        }
    }

    fun onCategorySelected(category: MovieCategory) {
        if (_selectedCategory.value == category) return
        _selectedCategory.value = category
    }
}
