package dev.brunofelix.movies.core.presentation.state

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed class UiEvent {
    data class ShowToast(
        val message: String,
        @get:StringRes val messageRes: Int = 0
    ): UiEvent()
    data class ShowSnackBar(
        val message: String? = null,
        @get:StringRes val messageRes: Int = 0
    ): UiEvent()

    companion object {
        private val _eventState = MutableSharedFlow<UiEvent>()
        val eventState = _eventState.asSharedFlow()

        suspend fun sendEvent(event: UiEvent) {
            _eventState.emit(event)
        }
    }
}