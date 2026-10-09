package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.presentation.util.UiState

data class ReleaseUiState(
    val months: List<ReleaseMonth> = emptyList(),
    val selectedMonth: ReleaseMonth = ReleaseMonth.current(),
    val theaters: UiState<List<Media>> = UiState.Initial,
    val streaming: UiState<List<Media>> = UiState.Initial,
    val series: UiState<List<Media>> = UiState.Initial
)
