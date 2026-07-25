package com.example.lbg.view

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// ✅ Add this
@RunWith(RobolectricTestRunner::class)
class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Shared state — wired up per test
    private var count by mutableStateOf(0)
    private val derivedCount get() = count * 2

    @Before
    fun setUp() {
        count = 0 // reset before each test
        composeTestRule.setContent {
            DashboardScreen(
                count = count,
                derivedCount = derivedCount,
                onIncrement = { count++ },
                onDecrement = { if (count > 0) count-- }
            )
        }
    }

    // ── Initial State ──────────────────────────────────────

    @Test
    fun initialState_displaysZeroCount() {
        // Assert
        composeTestRule.onNodeWithText("COUNT 0").assertExists()
        composeTestRule.onNodeWithText("× 2 = 0").assertExists()
    }

    /*@Test
    fun initialState_decrementButton_isDisabled() {
        // At count=0 decrement should be disabled
        composeTestRule
            .onNodeWithContentDescription("Decrement count")
            .assertIsNotEnabled()
    }

    @Test
    fun initialState_incrementButton_isEnabled() {
        composeTestRule
            .onNodeWithContentDescription("Increment count")
            .assertIsEnabled()
    }

    // ── Increment ──────────────────────────────────────────

    @Test
    fun clickingIncrement_updatesCountAndDerived() {
        // Act
        composeTestRule
            .onNodeWithContentDescription("Increment count")
            .performClick()

        // Assert — count updated
        composeTestRule.onNodeWithText("COUNT 1").assertExists()

        // Assert — derivedCount updated (1 * 2 = 2)
        composeTestRule.onNodeWithText("× 2 = 2").assertExists()
    }

    @Test
    fun clickingIncrement_multipleTimesUpdatesCorrectly() {
        // Act — click 3 times
        repeat(3) {
            composeTestRule
                .onNodeWithContentDescription("Increment count")
                .performClick()
        }

        // Assert
        composeTestRule.onNodeWithText("COUNT 3").assertExists()
        composeTestRule.onNodeWithText("× 2 = 6").assertExists()
    }

    // ── Decrement ──────────────────────────────────────────

    @Test
    fun clickingDecrement_afterIncrement_reducesCount() {
        // Arrange — increment first
        composeTestRule
            .onNodeWithContentDescription("Increment count")
            .performClick()
        composeTestRule.onNodeWithText("COUNT 1").assertExists()

        // Act — decrement
        composeTestRule
            .onNodeWithContentDescription("Decrement count")
            .performClick()

        // Assert
        composeTestRule.onNodeWithText("COUNT 0").assertExists()
    }

    @Test
    fun decrementAtZero_doesNotGoNegative() {
        // Arrange — count is already 0 from setUp()
        // Decrement button is disabled at 0 so click should have no effect
        // But let's verify the button is actually disabled
        composeTestRule
            .onNodeWithContentDescription("Decrement count")
            .assertIsNotEnabled()

        // Count should still be 0
        composeTestRule.onNodeWithText("COUNT 0").assertExists()
    }

    // ── Boundary ───────────────────────────────────────────

    @Test
    fun atMaxCount_incrementButton_isDisabled() {
        // Arrange — push to max
        count = 99 // set directly via shared state

        // Assert — increment disabled at boundary
        composeTestRule
            .onNodeWithContentDescription("Increment count")
            .assertIsNotEnabled()
    }*/
}