package dev.brunofelix.movies.core.data.util.extension

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.util.exception.LocalException
import dev.brunofelix.movies.core.domain.util.exception.RemoteException
import dev.brunofelix.movies.core.presentation.util.UiText

fun RemoteException.toUiText(): UiText {
    return when (this) {
        is RemoteException.Unauthorized -> UiText.StringResource(R.string.error_network_unauthorized)
        is RemoteException.NotFound -> UiText.StringResource(R.string.error_network_not_found)
        is RemoteException.ServerError -> UiText.StringResource(R.string.error_network_server)
        is RemoteException.NoInternet -> UiText.StringResource(R.string.error_network_no_internet)
        is RemoteException.Unknown -> UiText.StringResource(R.string.error_unknown)
        is RemoteException.ApiError -> UiText.DynamicString(this.message ?: "")
        is RemoteException.General -> UiText.StringResource(this.messageRes)
    }
}

fun LocalException.toUiText(): UiText {
    return when (this) {
        is LocalException.DatabaseError -> UiText.StringResource(R.string.error)
        is LocalException.PermissionDenied -> UiText.StringResource(R.string.error)
        is LocalException.DiskFull -> UiText.StringResource(R.string.error)
        is LocalException.Unknown -> UiText.StringResource(R.string.error_unknown)
        is LocalException.General -> UiText.StringResource(this.messageRes)
    }
}
