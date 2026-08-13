package dev.brunofelix.movies.core.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

/**
 * A generic [PagingSource] that can be used for any list that follows the TMDB pagination pattern.
 * @param T The type of data being paged.
 * @param fetch A lambda function that fetches the data for a specific page.
 */
class BasePagingSource<T : Any>(
    private val fetch: suspend (Int) -> Result<List<T>>
) : PagingSource<Int, T>() {

    private val pageLimit = 20

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(pageLimit) ?: anchorPage?.nextKey?.minus(pageLimit)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 1
            val response = fetch(page).getOrNull()
            LoadResult.Page(
                data = response ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isNullOrEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
