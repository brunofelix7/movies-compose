package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.MediaListCategory

/**
 * The list being shown. Its items come paged from [MediaListViewModel.medias].
 */
data class MediaListUiState(
    val category: MediaListCategory? = null,
    val month: ReleaseMonth? = null
)
