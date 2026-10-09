package dev.brunofelix.movies.test_util

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dev.brunofelix.movies.data.di.RepositoryModule
import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.model.Episode
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.model.enums.ReleaseType
import dev.brunofelix.movies.domain.repository.AppInfoRepository
import dev.brunofelix.movies.domain.repository.LanguageRepository
import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.repository.SearchRepository
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Singleton

object FakeData {
    val movie = Movie(id = 1L, title = "Dune", overview = "A desert planet.", voteAverage = 8.1f, duration = 155)
    val tvShow = TvShow(id = 2L, name = "Dark", overview = "A missing child.", voteAverage = 8.4f)
}

/**
 * Replaces the repositories with in-memory fakes, so navigation tests don't depend on the
 * network or on what is stored on the device.
 */
@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [RepositoryModule::class])
object FakeRepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRepository = object : MovieRepository {
        override suspend fun getDetails(id: Long): Resource<Movie> = Resource.Success(FakeData.movie)
        override suspend fun getPopularMovies(page: Int): Resource<List<Movie>> = Resource.Success(listOf(FakeData.movie))
        override suspend fun getUpcomingMovies(page: Int): Resource<List<Movie>> = Resource.Success(listOf(FakeData.movie))
        override suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>> = Resource.Success(listOf(FakeData.movie))
        override suspend fun getVideos(id: Long): Resource<List<Video>> = Resource.Success(emptyList())
        override suspend fun getCast(id: Long): Resource<List<Cast>> = Resource.Success(emptyList())
        override suspend fun getReleases(month: ReleaseMonth, type: ReleaseType, page: Int): Resource<List<Movie>> =
            Resource.Success(listOf(FakeData.movie))
    }

    @Provides
    @Singleton
    fun provideTvShowRepository(): TvShowRepository = object : TvShowRepository {
        override suspend fun getPopularTvShows(page: Int): Resource<List<TvShow>> = Resource.Success(listOf(FakeData.tvShow))
        override suspend fun getTopRatedTvShows(page: Int): Resource<List<TvShow>> = Resource.Success(listOf(FakeData.tvShow))
        override suspend fun getDetails(id: Long): Resource<TvShow> = Resource.Success(FakeData.tvShow)
        override suspend fun getVideos(id: Long): Resource<List<Video>> = Resource.Success(emptyList())
        override suspend fun getCast(id: Long): Resource<List<Cast>> = Resource.Success(emptyList())
        override suspend fun getSeasonEpisodes(id: Long, seasonNumber: Int): Resource<List<Episode>> =
            Resource.Success(emptyList())
        override suspend fun getReleases(month: ReleaseMonth, page: Int): Resource<List<TvShow>> =
            Resource.Success(listOf(FakeData.tvShow))
    }

    @Provides
    @Singleton
    fun provideMediaRepository(): MediaRepository = object : MediaRepository {
        private val favorites = MutableStateFlow(emptyList<Media>())

        override suspend fun save(media: Media): Resource<Unit> {
            favorites.update { it + media }
            return Resource.Success(Unit)
        }

        override suspend fun delete(media: Media): Resource<Unit> {
            favorites.update { list -> list.filterNot { it.id == media.id } }
            return Resource.Success(Unit)
        }

        override suspend fun isFavorite(id: Long): Resource<Boolean> =
            Resource.Success(favorites.value.any { it.id == id })

        override fun getFavoriteMedias(): Flow<List<Media>> = favorites
    }

    @Provides
    @Singleton
    fun provideLanguageRepository(): LanguageRepository = object : LanguageRepository {
        private val language = MutableStateFlow(LanguageEnum.ENGLISH)

        override fun getLanguage(): Flow<LanguageEnum> = language

        override suspend fun saveLanguage(language: LanguageEnum): Resource<Unit> {
            this.language.value = language
            return Resource.Success(Unit)
        }
    }

    @Provides
    @Singleton
    fun provideSearchRepository(): SearchRepository = object : SearchRepository {
        override suspend fun search(query: String, page: Int): Resource<List<Media>> = Resource.Success(emptyList())
    }

    @Provides
    @Singleton
    fun provideAppInfoRepository(): AppInfoRepository = object : AppInfoRepository {
        override fun getAppVersion(): String = "1.0.0-test"
    }
}
