package dev.brunofelix.movies.feature.tv_show.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.data.util.BasePagingSource
import dev.brunofelix.movies.core.data.util.extension.asPagerFlow
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.model.enums.TvShowCategory
import dev.brunofelix.movies.feature.tv_show.home.domain.use_case.GetPopularTvShowsUseCase
import dev.brunofelix.movies.feature.tv_show.home.domain.use_case.GetTopRatedTvShowsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TvShowHomeViewModel @Inject constructor(
    private val getPopularUseCase: GetPopularTvShowsUseCase,
    private val getTopRatedUseCase: GetTopRatedTvShowsUseCase
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow(TvShowCategory.POPULAR)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val pagingConfig = PagingConfig(pageSize = 20)

    private val popularTvShowsFlow = pagingConfig.asPagerFlow {
        BasePagingSource { getPopularUseCase(it) }
    }.cachedIn(viewModelScope)

    private val topRatedTvShowsFlow = pagingConfig.asPagerFlow {
        BasePagingSource { getTopRatedUseCase(it) }
    }.cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val tvShows: Flow<PagingData<TvShow>> = _selectedCategory.flatMapLatest { category ->
        when (category) {
            TvShowCategory.POPULAR -> popularTvShowsFlow
            TvShowCategory.TOP_RATED -> topRatedTvShowsFlow
        }
    }

    fun onCategorySelected(category: TvShowCategory) {
        if (_selectedCategory.value == category) return
        _selectedCategory.value = category
    }
}
