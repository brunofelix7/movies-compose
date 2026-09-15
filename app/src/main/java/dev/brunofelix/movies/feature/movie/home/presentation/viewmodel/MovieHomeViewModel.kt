package dev.brunofelix.movies.feature.movie.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.data.util.BasePagingSource
import dev.brunofelix.movies.core.data.util.extension.asPagerFlow
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.model.enums.MovieCategory
import dev.brunofelix.movies.core.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetPopularUseCase
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetTopRatedUseCase
import dev.brunofelix.movies.feature.movie.home.domain.use_case.GetUpcomingUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieHomeViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    private val getPopularUseCase: GetPopularUseCase,
    private val getUpcomingUseCase: GetUpcomingUseCase,
    private val getTopRatedUseCase: GetTopRatedUseCase
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(MovieCategory.POPULAR)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val pagingConfig = PagingConfig(pageSize = 20)

    private val language = getLanguageUseCase().distinctUntilChanged()

    private val popularMoviesFlow = pagerFlowPerLanguage { getPopularUseCase(it) }

    private val upcomingMoviesFlow = pagerFlowPerLanguage { getUpcomingUseCase(it) }

    private val topRatedMoviesFlow = pagerFlowPerLanguage { getTopRatedUseCase(it) }

    val movies: Flow<PagingData<Movie>> = _selectedCategory.flatMapLatest { category ->
        when (category) {
            MovieCategory.POPULAR -> popularMoviesFlow
            MovieCategory.UPCOMING -> upcomingMoviesFlow
            MovieCategory.TOP_RATED -> topRatedMoviesFlow
        }
    }

    /**
     * Rebuilds the pager whenever the preferred language changes, so the already
     * paginated pages are dropped and refetched in the newly selected language.
     */
    private fun pagerFlowPerLanguage(
        fetch: suspend (page: Int) -> Resource<List<Movie>>
    ): Flow<PagingData<Movie>> {
        return language
            .flatMapLatest { pagingConfig.asPagerFlow { BasePagingSource(fetch = fetch) } }
            .cachedIn(viewModelScope)
    }

    fun onCategorySelected(category: MovieCategory) {
        if (_selectedCategory.value == category) return
        _selectedCategory.value = category
    }
}
