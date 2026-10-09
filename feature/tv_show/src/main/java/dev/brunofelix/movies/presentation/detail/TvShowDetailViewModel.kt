package dev.brunofelix.movies.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.domain.use_case.GetSeasonEpisodesUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowCastUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowDetailUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowVideosUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowWatchProvidersUseCase
import dev.brunofelix.movies.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.domain.use_case.UpdateFavoriteWatchProvidersUseCase
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.extension.toMedia
import dev.brunofelix.movies.domain.util.extension.toWatchAvailability
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.mapper.toTrailerKey
import dev.brunofelix.movies.presentation.mapper.toUiModel
import dev.brunofelix.movies.presentation.model.EpisodeUiModel
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import dev.brunofelix.movies.presentation.util.extension.toUiText
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val getTvShowDetailUseCase: GetTvShowDetailUseCase,
    private val getTvShowVideosUseCase: GetTvShowVideosUseCase,
    private val getTvShowCastUseCase: GetTvShowCastUseCase,
    private val getTvShowWatchProvidersUseCase: GetTvShowWatchProvidersUseCase,
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase,
    private val saveMediaUseCase: SaveMediaUseCase,
    private val isFavoriteMediaUseCase: IsFavoriteMediaUseCase,
    private val deleteMediaUseCase: DeleteMediaUseCase,
    private val updateFavoriteWatchProvidersUseCase: UpdateFavoriteWatchProvidersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TvShowDetailUiState())
    val uiState: StateFlow<TvShowDetailUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<TvShowDetailUiEvent>()
    val uiEvent: Flow<TvShowDetailUiEvent> = _uiEvent.receiveAsFlow()

    private var tvShowId: Long? = null
    private var tvShow: TvShow? = null

    /** `null` when they could not be loaded, so a favorite saved now gets them on the next sync. */
    private var watchProviders: List<WatchProvider>? = null

    fun onAction(action: TvShowDetailUiAction) {
        when (action) {
            is TvShowDetailUiAction.OnLoad -> load(action.tvShowId)
            TvShowDetailUiAction.OnRetry -> tvShowId?.let { load(it, force = true) }
            TvShowDetailUiAction.OnFavoriteToggle -> toggleFavorite()
            TvShowDetailUiAction.OnBack -> viewModelScope.launch { _uiEvent.send(TvShowDetailUiEvent.NavigateBack) }
            is TvShowDetailUiAction.OnSeasonToggle -> toggleSeason(action.seasonNumber)
            is TvShowDetailUiAction.OnSeasonRetry -> loadEpisodes(action.seasonNumber)
        }
    }

    private fun load(id: Long, force: Boolean = false) {
        if (!force && tvShowId == id && _uiState.value.tvShow !is UiState.Error) return
        tvShowId = id

        viewModelScope.launch {
            _uiState.value = TvShowDetailUiState(tvShow = UiState.Loading)

            val details = async { getTvShowDetailUseCase(id) }
            val videos = async { getTvShowVideosUseCase(id) }
            val cast = async { getTvShowCastUseCase(id) }
            val providers = async { getTvShowWatchProvidersUseCase(id) }

            when (val result = details.await()) {
                is Resource.Success -> {
                    tvShow = result.data
                    watchProviders = (providers.await() as? Resource.Success)?.data
                    val trailerKey = (videos.await() as? Resource.Success<List<Video>>)?.data?.toTrailerKey()
                    val castList = (cast.await() as? Resource.Success)?.data.orEmpty().map { it.toUiModel() }
                    val uiModel = result.data.toUiModel().copy(
                        trailerKey = trailerKey,
                        cast = castList,
                        watchAvailability = watchProviders?.toWatchAvailability()
                    )
                    _uiState.update { it.copy(tvShow = UiState.Success(uiModel)) }
                    refreshFavorite(result.data.id)
                    refreshStoredWatchProviders(result.data.id)

                    // The first season starts expanded.
                    uiModel.seasons.firstOrNull()?.let { toggleSeason(it.seasonNumber) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(tvShow = UiState.Error(result.throwable.toUiText())) }
                }
            }
        }
    }

    /**
     * Collapses the season when it is already open, otherwise opens it and fetches its
     * episodes the first time they are needed.
     */
    private fun toggleSeason(seasonNumber: Int) {
        val current = _uiState.value.seasons

        if (current.expandedSeasonNumber == seasonNumber) {
            _uiState.update { it.copy(seasons = current.copy(expandedSeasonNumber = null)) }
            return
        }

        _uiState.update { it.copy(seasons = current.copy(expandedSeasonNumber = seasonNumber)) }

        if (current.episodesOf(seasonNumber) !is UiState.Success) {
            loadEpisodes(seasonNumber)
        }
    }

    private fun loadEpisodes(seasonNumber: Int) {
        val id = tvShow?.id ?: return

        viewModelScope.launch {
            updateEpisodes(seasonNumber, UiState.Loading)

            val state = when (val result = getSeasonEpisodesUseCase(id, seasonNumber)) {
                is Resource.Success -> if (result.data.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(result.data.map { it.toUiModel() })
                }
                is Resource.Error -> UiState.Error(result.throwable.toUiText())
            }
            updateEpisodes(seasonNumber, state)
        }
    }

    private fun updateEpisodes(seasonNumber: Int, state: UiState<List<EpisodeUiModel>>) {
        _uiState.update {
            it.copy(seasons = it.seasons.copy(episodes = it.seasons.episodes + (seasonNumber to state)))
        }
    }

    private fun toggleFavorite() {
        val media = tvShow?.toMedia()?.copy(watchProviders = watchProviders) ?: return
        viewModelScope.launch {
            val result = if (_uiState.value.isFavorite) deleteMediaUseCase(media) else saveMediaUseCase(media)
            if (result is Resource.Error) {
                val message = if (_uiState.value.isFavorite) R.string.delete_media_error else R.string.mark_favorite_error
                _uiEvent.send(TvShowDetailUiEvent.ShowToast(UiText.StringResource(message)))
            }
            refreshFavorite(media.id)
        }
    }

    private suspend fun refreshFavorite(id: Long) {
        when (val result = isFavoriteMediaUseCase(id)) {
            is Resource.Success -> _uiState.update { it.copy(isFavorite = result.data) }
            is Resource.Error -> _uiEvent.send(
                TvShowDetailUiEvent.ShowToast(UiText.StringResource(R.string.is_favorite_media_error))
            )
        }
    }

    /**
     * Keeps the streaming services of a favorite current, which is what the favorites filter
     * reads. A failure is silent: the stored ones stay until the next visit.
     */
    private suspend fun refreshStoredWatchProviders(id: Long) {
        val providers = watchProviders ?: return
        if (_uiState.value.isFavorite) updateFavoriteWatchProvidersUseCase(id, providers)
    }
}
