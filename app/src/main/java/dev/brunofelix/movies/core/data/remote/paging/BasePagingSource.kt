package dev.brunofelix.movies.core.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * A generic [PagingSource] that can be used for any list that follows the TMDB pagination pattern.
 * @param T The type of data being paged.
 * @param pageSize The number of items per page expected from the API. Default is 20 for TMDB.
 * @param fetch A lambda function that fetches the data for a specific page.
 */
class BasePagingSource<T : Any>(
    private val pageSize: Int = 20,
    private val fetch: suspend (Int) -> Result<List<T>>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return fetch(page).fold(
            onSuccess = { data ->
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (data.size < pageSize) null else page + 1
                )
            },
            onFailure = { cause ->
                LoadResult.Error(cause)
            }
        )
    }
}
