package dev.brunofelix.movies.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import app.cash.turbine.test
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest

/** Keeps preferences in memory so the spec doesn't depend on the host file system. */
private class InMemoryPreferencesDataStore : DataStore<Preferences> {
    private val state = MutableStateFlow(emptyPreferences())

    override val data: Flow<Preferences> = state

    override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences {
        return transform(state.value).also { state.value = it }
    }
}

class PreferenceStorageImplTest : DescribeSpec({

    fun storage() = PreferenceStorageImpl(InMemoryPreferencesDataStore())

    describe("put and get") {
        it("should store and read back every supported type") {
            runTest {
                val storage = storage()

                storage.put("string", "value")
                storage.put("int", 7)
                storage.put("boolean", true)
                storage.put("long", 9L)
                storage.put("float", 1.5f)

                storage.get("string", "") shouldBe "value"
                storage.get("int", 0) shouldBe 7
                storage.get("boolean", false) shouldBe true
                storage.get("long", 0L) shouldBe 9L
                storage.get("float", 0f) shouldBe 1.5f
            }
        }

        it("should return the default value for a missing key") {
            runTest {
                storage().get("missing", "default") shouldBe "default"
            }
        }

        it("should reject unsupported types") {
            runTest {
                val storage = storage()

                shouldThrow<IllegalArgumentException> { storage.put("list", listOf(1)) }
                shouldThrow<IllegalArgumentException> { storage.get("list", listOf(1)) }
            }
        }
    }

    describe("observe") {
        it("should emit the stored value and its updates") {
            runTest {
                val storage = storage()

                storage.observe("language", "en").test {
                    awaitItem() shouldBe "en"
                    storage.put("language", "es")
                    awaitItem() shouldBe "es"
                    cancelAndIgnoreRemainingEvents()
                }
            }
        }
    }

    describe("remove and clear") {
        it("should remove a single key") {
            runTest {
                val storage = storage()
                storage.put("int", 3)
                storage.put("string", "kept")

                storage.remove("int")

                storage.get("int", 0) shouldBe 0
                storage.get("string", "") shouldBe "kept"
            }
        }

        it("should clear every key") {
            runTest {
                val storage = storage()
                storage.put("int", 3)
                storage.put("string", "value")

                storage.clear()

                storage.get("int", 0) shouldBe 0
                storage.get("string", "") shouldBe ""
            }
        }
    }
})
