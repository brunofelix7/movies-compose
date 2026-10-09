package dev.brunofelix.movies.data.repository

import app.cash.turbine.test
import dev.brunofelix.movies.data.local.source.MediaLocalDataSource
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.exception.LocalException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class MediaRepositoryImplTest : DescribeSpec({

    val localDataSource = mockk<MediaLocalDataSource>()
    val repository = MediaRepositoryImpl(localDataSource)
    val media = Media(id = 1L, title = "Dune")
    val error = LocalException.DatabaseError()

    beforeTest { clearAllMocks() }

    describe("save") {
        it("should return Success when the media is stored") {
            runTest {
                coEvery { localDataSource.insert(media) } returns Result.success(Unit)

                repository.save(media) shouldBe Resource.Success(Unit)
            }
        }

        it("should return Error when the media cannot be stored") {
            runTest {
                coEvery { localDataSource.insert(media) } returns Result.failure(error)

                repository.save(media) shouldBe Resource.Error(error)
            }
        }
    }

    describe("delete") {
        it("should return Success when the media is removed") {
            runTest {
                coEvery { localDataSource.delete(media) } returns Result.success(Unit)

                repository.delete(media) shouldBe Resource.Success(Unit)
            }
        }

        it("should return Error when the media cannot be removed") {
            runTest {
                coEvery { localDataSource.delete(media) } returns Result.failure(error)

                repository.delete(media) shouldBe Resource.Error(error)
            }
        }
    }

    describe("isFavorite") {
        it("should be true when the media is stored") {
            runTest {
                coEvery { localDataSource.getById(1L) } returns Result.success(media)

                repository.isFavorite(1L) shouldBe Resource.Success(true)
            }
        }

        it("should be false when the media is not stored") {
            runTest {
                coEvery { localDataSource.getById(1L) } returns Result.success(null)

                repository.isFavorite(1L) shouldBe Resource.Success(false)
            }
        }

        it("should return Error when the lookup fails") {
            runTest {
                coEvery { localDataSource.getById(1L) } returns Result.failure(error)

                repository.isFavorite(1L) shouldBe Resource.Error(error)
            }
        }
    }

    describe("updateWatchProviders") {
        val providers = listOf(WatchProvider(id = 8L))

        it("should return Success when the providers are stored") {
            runTest {
                coEvery { localDataSource.updateWatchProviders(1L, providers) } returns Result.success(Unit)

                repository.updateWatchProviders(1L, providers) shouldBe Resource.Success(Unit)
            }
        }

        it("should return Error when the providers cannot be stored") {
            runTest {
                coEvery { localDataSource.updateWatchProviders(1L, providers) } returns Result.failure(error)

                repository.updateWatchProviders(1L, providers) shouldBe Resource.Error(error)
            }
        }
    }

    describe("getFavoriteMedias") {
        it("should emit the stored medias") {
            runTest {
                every { localDataSource.getAll() } returns flowOf(listOf(media))

                repository.getFavoriteMedias().test {
                    awaitItem() shouldBe listOf(media)
                    awaitComplete()
                }
            }
        }
    }
})
