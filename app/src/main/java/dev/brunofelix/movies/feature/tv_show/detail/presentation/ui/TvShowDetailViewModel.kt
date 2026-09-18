package dev.brunofelix.movies.feature.tv_show.detail.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.core.presentation.util.extension.toUiText
import dev.brunofelix.movies.core.domain.mapper.toMedia
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.core.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.core.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.presentation.mapper.toUiModel
import dev.brunofelix.movies.core.presentation.ui.model.EpisodeUiModel
import dev.brunofelix.movies.core.presentation.ui.model.TvShowUiModel
import dev.brunofelix.movies.core.presentation.util.UiState
import dev.brunofelix.movies.feature.tv_show.detail.domain.use_case.GetSeasonEpisodesUseCase
import dev.brunofelix.movies.feature.tv_show.detail.domain.use_case.GetTvShowCastUseCase
import dev.brunofelix.movies.feature.tv_show.detail.domain.use_case.GetTvShowDetailUseCase
import dev.brunofelix.movies.feature.tv_show.detail.domain.use_case.GetTvShowVideosUseCase
import dev.brunofelix.movies.feature.tv_show.detail.presentation.state.SeasonsState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val getTvShowDetailUseCase: GetTvShowDetailUseCase,
    private val getTvShowVideosUseCase: GetTvShowVideosUseCase,
    private val getTvShowCastUseCase: GetTvShowCastUseCase,
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase,
    private val saveMediaUseCase: SaveMediaUseCase,
    private val isFavoriteMediaUseCase: IsFavoriteMediaUseCase,
    private val deleteMediaUseCase: DeleteMediaUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<TvShowUiModel>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _seasonsState = MutableStateFlow(SeasonsState())
    val seasonsState = _seasonsState.asStateFlow()

    private var tvShowDomain: TvShow? = null

    fun getDetails(tvShowId: Long) {
        val currentState = _uiState.value
        if (currentState is UiState.Success && tvShowDomain?.id == tvShowId) {
            return
        }
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _isFavorite.value = false
            _seasonsState.value = SeasonsState()

            val detailsDeferred = async { getTvShowDetailUseCase(tvShowId) }
            val videosDeferred = async { getTvShowVideosUseCase(tvShowId) }
            val castDeferred = async { getTvShowCastUseCase(tvShowId) }

            val detailsResult = detailsDeferred.await()
            val videosResult = videosDeferred.await()
            val castResult = castDeferred.await()

            if (detailsResult is Resource.Success) {
                tvShowDomain = detailsResult.data
                val trailerKey = if (videosResult is Resource.Success) {
                    videosResult.data.find {
                        it.site.equals("YouTube", ignoreCase = true) &&
                                it.type.equals("Trailer", ignoreCase = true)
                    }?.key ?: videosResult.data.firstOrNull {
                        it.site.equals("YouTube", ignoreCase = true)
                    }?.key
                } else null
                val cast = if (castResult is Resource.Success) {
                    castResult.data.map { it.toUiModel() }
                } else emptyList()

                _uiState.value = UiState.Success(
                    detailsResult.data.toUiModel().copy(trailerKey = trailerKey, cast = cast)
                )
                _isFavorite.value = isFavoriteMediaUseCase(detailsResult.data.id)
            } else if (detailsResult is Resource.Error) {
                _uiState.value = UiState.Error(detailsResult.throwable.toUiText())
            }
        }
    }

    /**
     * Collapses the season when it is already open, otherwise opens it and fetches its
     * episodes the first time they are needed.
     */
    fun onSeasonToggle(seasonNumber: Int) {
        val current = _seasonsState.value

        if (current.expandedSeasonNumber == seasonNumber) {
            _seasonsState.value = current.copy(expandedSeasonNumber = null)
            return
        }

        _seasonsState.value = current.copy(expandedSeasonNumber = seasonNumber)

        if (current.episodesOf(seasonNumber) !is UiState.Success) {
            loadEpisodes(seasonNumber)
        }
    }

    fun onSeasonRetry(seasonNumber: Int) = loadEpisodes(seasonNumber)

    private fun loadEpisodes(seasonNumber: Int) {
        val tvShowId = tvShowDomain?.id ?: return

        viewModelScope.launch {
            updateEpisodes(seasonNumber, UiState.Loading)

            val result = getSeasonEpisodesUseCase(tvShowId, seasonNumber)
            updateEpisodes(
                seasonNumber = seasonNumber,
                state = when (result) {
                    is Resource.Success -> if (result.data.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(result.data.map { it.toUiModel() })
                    }
                    is Resource.Error -> UiState.Error(result.throwable.toUiText())
                }
            )
        }
    }

    private fun updateEpisodes(seasonNumber: Int, state: UiState<List<EpisodeUiModel>>) {
        _seasonsState.update { it.copy(episodes = it.episodes + (seasonNumber to state)) }
    }

    fun onFavoriteToggle() = viewModelScope.launch {
        tvShowDomain?.let { tvShow ->
            if (_isFavorite.value) {
                deleteMediaUseCase(tvShow.toMedia())
            } else {
                saveMediaUseCase(tvShow.toMedia())
            }
            _isFavorite.value = isFavoriteMediaUseCase(tvShow.id)
        }
    }
}
