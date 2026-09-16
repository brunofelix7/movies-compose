package dev.brunofelix.movies.feature.tv_show.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.presentation.util.BasePagingSource
import dev.brunofelix.movies.core.presentation.util.extension.asPagerFlow
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.model.enums.TvShowCategory
import dev.brunofelix.movies.core.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.feature.tv_show.home.domain.use_case.GetPopularTvShowsUseCase
import dev.brunofelix.movies.feature.tv_show.home.domain.use_case.GetTopRatedTvShowsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TvShowHomeViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    private val getPopularUseCase: GetPopularTvShowsUseCase,
    private val getTopRatedUseCase: GetTopRatedTvShowsUseCase
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(TvShowCategory.POPULAR)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val pagingConfig = PagingConfig(pageSize = 20)

    private val language = getLanguageUseCase().distinctUntilChanged()

    private val popularTvShowsFlow = pagerFlowPerLanguage { getPopularUseCase(it) }

    private val topRatedTvShowsFlow = pagerFlowPerLanguage { getTopRatedUseCase(it) }

    val tvShows: Flow<PagingData<TvShow>> = _selectedCategory.flatMapLatest { category ->
        when (category) {
            TvShowCategory.POPULAR -> popularTvShowsFlow
            TvShowCategory.TOP_RATED -> topRatedTvShowsFlow
        }
    }

    /**
     * Rebuilds the pager whenever the preferred language changes, so the already
     * paginated pages are dropped and refetched in the newly selected language.
     */
    private fun pagerFlowPerLanguage(
        fetch: suspend (page: Int) -> Resource<List<TvShow>>
    ): Flow<PagingData<TvShow>> {
        return language
            .flatMapLatest { pagingConfig.asPagerFlow { BasePagingSource(fetch = fetch) } }
            .cachedIn(viewModelScope)
    }

    fun onCategorySelected(category: TvShowCategory) {
        if (_selectedCategory.value == category) return
        _selectedCategory.value = category
    }
}
