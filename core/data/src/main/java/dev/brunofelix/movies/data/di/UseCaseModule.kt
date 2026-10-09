package dev.brunofelix.movies.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.brunofelix.movies.data.use_case.DeleteMediaUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetAppVersionUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetFavoriteMediasUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetLanguageUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetMediaListUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetMovieCastUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetMovieDetailUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetMovieReleasesUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetMovieVideosUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetPopularMoviesUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetPopularTvShowsUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetSeasonEpisodesUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetTopRatedMoviesUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetTopRatedTvShowsUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetTvShowCastUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetTvShowDetailUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetTvShowReleasesUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetTvShowVideosUseCaseImpl
import dev.brunofelix.movies.data.use_case.GetUpcomingMoviesUseCaseImpl
import dev.brunofelix.movies.data.use_case.IsFavoriteMediaUseCaseImpl
import dev.brunofelix.movies.data.use_case.SaveLanguageUseCaseImpl
import dev.brunofelix.movies.data.use_case.SaveMediaUseCaseImpl
import dev.brunofelix.movies.data.use_case.SearchMediaUseCaseImpl
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.domain.use_case.GetAppVersionUseCase
import dev.brunofelix.movies.domain.use_case.GetFavoriteMediasUseCase
import dev.brunofelix.movies.domain.use_case.GetLanguageUseCase
import dev.brunofelix.movies.domain.use_case.GetMediaListUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieCastUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieDetailUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieReleasesUseCase
import dev.brunofelix.movies.domain.use_case.GetMovieVideosUseCase
import dev.brunofelix.movies.domain.use_case.GetPopularMoviesUseCase
import dev.brunofelix.movies.domain.use_case.GetPopularTvShowsUseCase
import dev.brunofelix.movies.domain.use_case.GetSeasonEpisodesUseCase
import dev.brunofelix.movies.domain.use_case.GetTopRatedMoviesUseCase
import dev.brunofelix.movies.domain.use_case.GetTopRatedTvShowsUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowCastUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowDetailUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowReleasesUseCase
import dev.brunofelix.movies.domain.use_case.GetTvShowVideosUseCase
import dev.brunofelix.movies.domain.use_case.GetUpcomingMoviesUseCase
import dev.brunofelix.movies.domain.use_case.IsFavoriteMediaUseCase
import dev.brunofelix.movies.domain.use_case.SaveLanguageUseCase
import dev.brunofelix.movies.domain.use_case.SaveMediaUseCase
import dev.brunofelix.movies.domain.use_case.SearchMediaUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindDeleteMediaUseCase(
        impl: DeleteMediaUseCaseImpl
    ): DeleteMediaUseCase

    @Binds
    abstract fun bindGetAppVersionUseCase(
        impl: GetAppVersionUseCaseImpl
    ): GetAppVersionUseCase

    @Binds
    abstract fun bindGetFavoriteMediasUseCase(
        impl: GetFavoriteMediasUseCaseImpl
    ): GetFavoriteMediasUseCase

    @Binds
    abstract fun bindGetLanguageUseCase(
        impl: GetLanguageUseCaseImpl
    ): GetLanguageUseCase

    @Binds
    abstract fun bindGetMediaListUseCase(
        impl: GetMediaListUseCaseImpl
    ): GetMediaListUseCase

    @Binds
    abstract fun bindGetMovieCastUseCase(
        impl: GetMovieCastUseCaseImpl
    ): GetMovieCastUseCase

    @Binds
    abstract fun bindGetMovieDetailUseCase(
        impl: GetMovieDetailUseCaseImpl
    ): GetMovieDetailUseCase

    @Binds
    abstract fun bindGetMovieReleasesUseCase(
        impl: GetMovieReleasesUseCaseImpl
    ): GetMovieReleasesUseCase

    @Binds
    abstract fun bindGetMovieVideosUseCase(
        impl: GetMovieVideosUseCaseImpl
    ): GetMovieVideosUseCase

    @Binds
    abstract fun bindGetPopularMoviesUseCase(
        impl: GetPopularMoviesUseCaseImpl
    ): GetPopularMoviesUseCase

    @Binds
    abstract fun bindGetPopularTvShowsUseCase(
        impl: GetPopularTvShowsUseCaseImpl
    ): GetPopularTvShowsUseCase

    @Binds
    abstract fun bindGetSeasonEpisodesUseCase(
        impl: GetSeasonEpisodesUseCaseImpl
    ): GetSeasonEpisodesUseCase

    @Binds
    abstract fun bindGetTopRatedMoviesUseCase(
        impl: GetTopRatedMoviesUseCaseImpl
    ): GetTopRatedMoviesUseCase

    @Binds
    abstract fun bindGetTopRatedTvShowsUseCase(
        impl: GetTopRatedTvShowsUseCaseImpl
    ): GetTopRatedTvShowsUseCase

    @Binds
    abstract fun bindGetTvShowCastUseCase(
        impl: GetTvShowCastUseCaseImpl
    ): GetTvShowCastUseCase

    @Binds
    abstract fun bindGetTvShowDetailUseCase(
        impl: GetTvShowDetailUseCaseImpl
    ): GetTvShowDetailUseCase

    @Binds
    abstract fun bindGetTvShowReleasesUseCase(
        impl: GetTvShowReleasesUseCaseImpl
    ): GetTvShowReleasesUseCase

    @Binds
    abstract fun bindGetTvShowVideosUseCase(
        impl: GetTvShowVideosUseCaseImpl
    ): GetTvShowVideosUseCase

    @Binds
    abstract fun bindGetUpcomingMoviesUseCase(
        impl: GetUpcomingMoviesUseCaseImpl
    ): GetUpcomingMoviesUseCase

    @Binds
    abstract fun bindIsFavoriteMediaUseCase(
        impl: IsFavoriteMediaUseCaseImpl
    ): IsFavoriteMediaUseCase

    @Binds
    abstract fun bindSaveLanguageUseCase(
        impl: SaveLanguageUseCaseImpl
    ): SaveLanguageUseCase

    @Binds
    abstract fun bindSaveMediaUseCase(
        impl: SaveMediaUseCaseImpl
    ): SaveMediaUseCase

    @Binds
    abstract fun bindSearchMediaUseCase(
        impl: SearchMediaUseCaseImpl
    ): SearchMediaUseCase
}
