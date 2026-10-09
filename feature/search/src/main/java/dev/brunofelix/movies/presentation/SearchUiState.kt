package dev.brunofelix.movies.presentation

/**
 * @param isSearchTriggered whether the current [query] was already sent. It stays false while
 * the query is being debounced, so the results on screen are kept instead of flashing a spinner.
 */
data class SearchUiState(
    val query: String = "",
    val isSearchTriggered: Boolean = false
)
