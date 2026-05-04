package dev.brunofelix.movies.core.data.api.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.brunofelix.movies.core.data.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.util.logError
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePopularPagingSource @Inject constructor(
    private val dataSource: MovieRemoteDataSource
) : PagingSource<Int, Movie>() {

    private val pageLimit = 20

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(pageLimit) ?: anchorPage?.nextKey?.minus(pageLimit)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = dataSource.getPopular(page).getOrNull()
            LoadResult.Page(
                data = response ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isNullOrEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            logError(e)
            LoadResult.Error(e)
        } catch (e: HttpException) {
            logError(e)
            LoadResult.Error(e)
        }
    }
}