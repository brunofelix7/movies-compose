package dev.brunofelix.movies.presentation.util.extension

import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.RemoteException
import dev.brunofelix.movies.core.presentation.R
import dev.brunofelix.movies.presentation.util.UiState
import dev.brunofelix.movies.presentation.util.UiText
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ResourceExtTest : DescribeSpec({

    describe("toUiState") {
        it("should map a non empty success into Success with the transformed items") {
            Resource.Success(listOf(1, 2)).toUiState { list -> list.map { it * 10 } } shouldBe
                UiState.Success(listOf(10, 20))
        }

        it("should collapse an empty result into Empty") {
            Resource.Success(listOf(1)).toUiState { emptyList<Int>() } shouldBe UiState.Empty
        }

        it("should map an error into Error with its message") {
            val error: Resource<List<Int>> = Resource.Error(RemoteException.NoInternet())

            error.toUiState { it } shouldBe UiState.Error(UiText.StringResource(R.string.error_network_no_internet))
        }
    }
})
