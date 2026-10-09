package dev.brunofelix.movies.presentation

import dev.brunofelix.movies.domain.model.Media

sealed interface SearchUiAction {
    data class OnQueryChange(val query: String) : SearchUiAction
    data object OnSearch : SearchUiAction
    data object OnClose : SearchUiAction
    data object OnSessionEnd : SearchUiAction
    data class OnMediaClick(val media: Media) : SearchUiAction
}
