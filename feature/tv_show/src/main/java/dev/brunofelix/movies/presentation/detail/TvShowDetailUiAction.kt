package dev.brunofelix.movies.presentation.detail

sealed interface TvShowDetailUiAction {
    data class OnLoad(val tvShowId: Long) : TvShowDetailUiAction
    data object OnRetry : TvShowDetailUiAction
    data object OnFavoriteToggle : TvShowDetailUiAction
    data object OnBack : TvShowDetailUiAction
    data class OnSeasonToggle(val seasonNumber: Int) : TvShowDetailUiAction
    data class OnSeasonRetry(val seasonNumber: Int) : TvShowDetailUiAction
}
