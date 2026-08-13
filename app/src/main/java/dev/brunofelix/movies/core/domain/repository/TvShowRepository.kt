package dev.brunofelix.movies.core.domain.repository

import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.domain.model.TvShow

interface TvShowRepository {
    suspend fun getPopulars(page: Int): Resource<List<TvShow>>
    suspend fun getTopRated(page: Int): Resource<List<TvShow>>
    suspend fun getDetails(id: Long): Resource<TvShow>
}
