package dev.brunofelix.movies.data.local.source

import app.cash.turbine.test
import dev.brunofelix.movies.data.local.dao.MediaDao
import dev.brunofelix.movies.data.local.entity.MediaEntity
import dev.brunofelix.movies.data.local.mapper.toEntity
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.domain.util.exception.LocalException
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class MediaLocalDataSourceImplTest : DescribeSpec({

    val dao = mockk<MediaDao>()
    val dataSource = MediaLocalDataSourceImpl(dao)
    val media = Media(id = 1L, title = "Dune", posterPath = "url", type = MediaType.MOVIE)
    val entity: MediaEntity = media.toEntity()

    beforeTest { clearAllMocks() }

    describe("insert") {
        it("should store the media as an entity") {
            runTest {
                coEvery { dao.insert(entity) } returns 1L

                dataSource.insert(media) shouldBe Result.success(Unit)
                coVerify { dao.insert(entity) }
            }
        }

        it("should fail with a DatabaseError when the DAO throws") {
            runTest {
                coEvery { dao.insert(any()) } throws IllegalStateException()

                dataSource.insert(media).exceptionOrNull().shouldBeInstanceOf<LocalException.DatabaseError>()
            }
        }
    }

    describe("delete") {
        it("should delete the media entity") {
            runTest {
                coEvery { dao.delete(entity) } returns 1

                dataSource.delete(media) shouldBe Result.success(Unit)
            }
        }

        it("should fail with a DatabaseError when the DAO throws") {
            runTest {
                coEvery { dao.delete(any()) } throws IllegalStateException()

                dataSource.delete(media).exceptionOrNull().shouldBeInstanceOf<LocalException.DatabaseError>()
            }
        }
    }

    describe("getById") {
        it("should return the stored media") {
            runTest {
                coEvery { dao.getById(1L) } returns entity

                dataSource.getById(1L) shouldBe Result.success(media)
            }
        }

        it("should return null when the media is not stored") {
            runTest {
                coEvery { dao.getById(2L) } returns null

                dataSource.getById(2L).getOrThrow().shouldBeNull()
            }
        }
    }

    describe("getAll") {
        it("should emit the stored medias as domain models") {
            runTest {
                every { dao.getAll() } returns flowOf(listOf(entity))

                dataSource.getAll().test {
                    awaitItem() shouldBe listOf(media)
                    awaitComplete()
                }
            }
        }
    }
})
