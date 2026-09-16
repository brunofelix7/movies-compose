package dev.brunofelix.movies.feature.media_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.core.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.core.presentation.util.BasePagingSource
import dev.brunofelix.movies.core.presentation.util.extension.asPagerFlow
import dev.brunofelix.movies.feature.media_list.domain.use_case.GetMediaListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

private const val PAGE_SIZE = 20

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MediaListViewModel @Inject constructor(
    getLanguageUseCase: GetLanguageUseCase,
    private val getMediaListUseCase: GetMediaListUseCase
) : ViewModel() {

    private data class Args(val category: MediaListCategory, val month: ReleaseMonth?)

    private val pagingConfig = PagingConfig(pageSize = PAGE_SIZE)

    private val _args = MutableStateFlow<Args?>(null)

    /**
     * Rebuilt when the list changes or the preferred language changes, like the home pagers.
     */
    val medias: Flow<PagingData<Media>> = combine(
        _args.filterNotNull().distinctUntilChanged(),
        getLanguageUseCase().distinctUntilChanged()
    ) { args, _ -> args }
        .flatMapLatest { args ->
            pagingConfig.asPagerFlow {
                BasePagingSource { page ->
                    getMediaListUseCase(args.category, args.month, page)
                }
            }
        }
        .cachedIn(viewModelScope)

    fun load(category: MediaListCategory, month: ReleaseMonth?) {
        _args.value = Args(category = category, month = month)
    }
}
