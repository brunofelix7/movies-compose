package dev.brunofelix.movies.presentation.util

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val TIMEOUT_MILLIS = 5_000L

@RunWith(AndroidJUnit4::class)
class ObserveAsEventsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDeliverEveryEventSentToTheFlow() {
        val channel = Channel<String>(Channel.BUFFERED)
        val received = mutableListOf<String>()
        composeTestRule.setContent {
            ObserveAsEvents(channel.receiveAsFlow()) { received += it }
        }

        runBlocking {
            channel.send("first")
            channel.send("second")
        }
        composeTestRule.waitUntil(timeoutMillis = TIMEOUT_MILLIS) { received.size == 2 }

        received shouldBe listOf("first", "second")
    }
}
