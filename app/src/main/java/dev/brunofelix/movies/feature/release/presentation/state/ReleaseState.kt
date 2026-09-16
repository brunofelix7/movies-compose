package dev.brunofelix.movies.feature.release.presentation.state

import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.presentation.util.UiState

data class ReleaseState(
    val months: List<ReleaseMonth> = emptyList(),
    val selectedMonth: ReleaseMonth = ReleaseMonth.current(),
    val theaters: UiState<List<Media>> = UiState.Initial,
    val streaming: UiState<List<Media>> = UiState.Initial,
    val series: UiState<List<Media>> = UiState.Initial
)
