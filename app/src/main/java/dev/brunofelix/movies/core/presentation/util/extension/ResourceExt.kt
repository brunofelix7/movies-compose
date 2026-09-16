package dev.brunofelix.movies.core.presentation.util.extension

import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.domain.util.fold
import dev.brunofelix.movies.core.presentation.util.UiState

/**
 * Converts a list [Resource] into the [UiState] the screens consume, collapsing an empty
 * result into [UiState.Empty].
 */
fun <T, R> Resource<List<T>>.toUiState(transform: (List<T>) -> List<R>): UiState<List<R>> {
    return fold(
        onSuccess = { data ->
            val items = transform(data)
            if (items.isEmpty()) UiState.Empty else UiState.Success(items)
        },
        onFailure = { throwable -> UiState.Error(throwable.toUiText()) }
    )
}
