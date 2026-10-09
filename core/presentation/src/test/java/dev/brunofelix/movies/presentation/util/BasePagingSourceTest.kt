package dev.brunofelix.movies.presentation.util

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.brunofelix.movies.domain.util.Resource
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.test.runTest

class BasePagingSourceTest : DescribeSpec({

    fun refresh(key: Int? = null) = PagingSource.LoadParams.Refresh(key, loadSize = 2, placeholdersEnabled = false)

    describe("load") {
        it("should start at page 1 and point to the next page when the page is full") {
            runTest {
                val requested = mutableListOf<Int>()
                val source = BasePagingSource(pageSize = 2) { page ->
                    requested += page
                    Resource.Success(listOf("a", "b"))
                }

                val result = source.load(refresh())

                requested shouldBe listOf(1)
                result.shouldBeInstanceOf<PagingSource.LoadResult.Page<Int, String>>()
                result.data shouldBe listOf("a", "b")
                result.prevKey.shouldBeNull()
                result.nextKey shouldBe 2
            }
        }

        it("should treat a page smaller than the page size as the last one") {
            runTest {
                val source = BasePagingSource(pageSize = 2) { Resource.Success(listOf("a")) }

                val result = source.load(refresh(key = 3)) as PagingSource.LoadResult.Page

                result.prevKey shouldBe 2
                result.nextKey.shouldBeNull()
            }
        }

        it("should return an Error when the fetch fails") {
            runTest {
                val failure = IllegalStateException("boom")
                val source = BasePagingSource<String>(pageSize = 2) { Resource.Error(failure) }

                val result = source.load(refresh())

                result.shouldBeInstanceOf<PagingSource.LoadResult.Error<Int, String>>()
                result.throwable shouldBe failure
            }
        }
    }

    describe("getRefreshKey") {
        it("should return null when there is no anchor position") {
            val source = BasePagingSource<String> { Resource.Success(emptyList()) }
            val state = PagingState<Int, String>(
                pages = emptyList(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 2),
                leadingPlaceholderCount = 0
            )

            source.getRefreshKey(state).shouldBeNull()
        }

        it("should return the page of the anchor position") {
            val source = BasePagingSource<String> { Resource.Success(emptyList()) }
            val page = PagingSource.LoadResult.Page(data = listOf("a", "b"), prevKey = 1, nextKey = 3)
            val state = PagingState(
                pages = listOf(page),
                anchorPosition = 0,
                config = PagingConfig(pageSize = 2),
                leadingPlaceholderCount = 0
            )

            source.getRefreshKey(state) shouldBe 2
        }
    }
})
