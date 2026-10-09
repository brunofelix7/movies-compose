package dev.brunofelix.movies.presentation.util.extension

import dev.brunofelix.movies.domain.util.exception.LocalException
import dev.brunofelix.movies.domain.util.exception.RemoteException
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.util.UiText
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class DataErrorExtTest : DescribeSpec({

    describe("RemoteException.toUiText") {
        it("should map each network error to its message") {
            RemoteException.Unauthorized().toUiText() shouldBe UiText.StringResource(R.string.error_network_unauthorized)
            RemoteException.NotFound().toUiText() shouldBe UiText.StringResource(R.string.error_network_not_found)
            RemoteException.ServerError().toUiText() shouldBe UiText.StringResource(R.string.error_network_server)
            RemoteException.NoInternet().toUiText() shouldBe UiText.StringResource(R.string.error_network_no_internet)
            RemoteException.Unknown().toUiText() shouldBe UiText.StringResource(R.string.error_unknown)
        }

        it("should show the API message as is") {
            RemoteException.ApiError(code = 7, message = "Invalid API key").toUiText() shouldBe
                UiText.DynamicString("Invalid API key")
        }

        it("should use the resource of a General error") {
            RemoteException.General(messageRes = 99).toUiText() shouldBe UiText.StringResource(99)
        }
    }

    describe("LocalException.toUiText") {
        it("should map each local error to its message") {
            LocalException.DatabaseError().toUiText() shouldBe UiText.StringResource(R.string.error_local_database)
            LocalException.PermissionDenied().toUiText() shouldBe UiText.StringResource(R.string.error_local_permission_denied)
            LocalException.DiskFull().toUiText() shouldBe UiText.StringResource(R.string.error_local_disk_full)
            LocalException.Unknown().toUiText() shouldBe UiText.StringResource(R.string.error_unknown)
            LocalException.General(messageRes = 98).toUiText() shouldBe UiText.StringResource(98)
        }
    }

    describe("Throwable.toUiText") {
        it("should delegate to the remote and local mappings") {
            (RemoteException.NoInternet() as Throwable).toUiText() shouldBe
                UiText.StringResource(R.string.error_network_no_internet)
            (LocalException.DiskFull() as Throwable).toUiText() shouldBe
                UiText.StringResource(R.string.error_local_disk_full)
        }

        it("should fall back to the unknown error for anything else") {
            IllegalStateException().toUiText() shouldBe UiText.StringResource(R.string.error_unknown)
        }
    }
})
