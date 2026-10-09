package dev.brunofelix.movies.presentation.util.extension

import androidx.paging.LoadState
import androidx.paging.PagingConfig
import androidx.paging.testing.asSnapshot
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.presentation.util.BasePagingSource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

class PagingExtTest : DescribeSpec({

    describe("asPagerFlow") {
        it("should page through the source until a short page") {
            runTest {
                val pages = mapOf(1 to listOf("a", "b"), 2 to listOf("c"))
                val flow = PagingConfig(pageSize = 2, initialLoadSize = 2, prefetchDistance = 1).asPagerFlow {
                    BasePagingSource(pageSize = 2) { page -> Resource.Success(pages.getValue(page)) }
                }

                flow.asSnapshot { scrollTo(index = 2) } shouldBe listOf("a", "b", "c")
            }
        }
    }

    describe("createCombinedLoadStates") {
        it("should default every state to not loading") {
            val states = createCombinedLoadStates()

            states.refresh shouldBe LoadState.NotLoading(false)
            states.append shouldBe LoadState.NotLoading(false)
            states.prepend shouldBe LoadState.NotLoading(false)
        }

        it("should mirror the given states in the source states") {
            val states = createCombinedLoadStates(refresh = LoadState.Loading)

            states.refresh shouldBe LoadState.Loading
            states.source.refresh shouldBe LoadState.Loading
        }
    }
})
